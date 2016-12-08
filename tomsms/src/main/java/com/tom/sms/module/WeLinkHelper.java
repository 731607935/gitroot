package com.tom.sms.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WeLinkHelper {
	private static final Logger LOGGER = Logger.getLogger(WeLinkHelper.class);
	/** 短信发送地址 */
	private static String smsSendUrl;
	/** 上行短信查询地址 */
	private static String smsQueryUrl;
	/** 短信下行状态查询地址 */
	private static String smsSendStatusUrl;
	/** 账号 */
	private static String account;
	/** 密码 */
	private static String password;
	/** 产品编号 */
	private static String prdid;
	
	/**
	 * 发送短信
	 * @param mobile 手机号，单次发送一个手机
	 * @param content 短信内容
	 * @return 推送成功：消息ID;推送失败：推送返回消息
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException 
	 */
	public static String smsSend(String mobile, String content) throws UnsupportedEncodingException, DocumentException {
		String postDate = "sname="+account+"&spwd="+password+"&scorpid=&sprdid="+prdid+"&sdst="+mobile+"&smsg="+URLEncoder.encode(content, "UTF-8");
		String postResult = send(postDate, smsSendUrl);
		Map<String, String> map = getElementData(postResult);
		if ("0".equals(map.get("State")))
			return map.get("MsgID");
		else
			return map.get("MsgState");
	}
	
	/**
	 * 查询上行短信，查询过的上行短信内容将不再显示
	 * @return
	 * @throws DocumentException
	 */
	public static List<WelinkUpSms> smsQuery() throws DocumentException {
		String postData = "accountid="+account+"&password="+password;
		String postResult = send(postData, smsQueryUrl);
		Document document = DocumentHelper.parseText(postResult);
		List<Map<String, String>> list = getElementData(document.getRootElement(), "MoInfos", "MoInfo");
		List<WelinkUpSms> objs = new ArrayList<WelinkUpSms>();
		for (Map<String, String> map : list) {
			WelinkUpSms obj = new WelinkUpSms();
			obj.setMoTime(map.get("MOTime"));
			obj.setMsgId(map.get("MsgId"));
			obj.setUpUserMsg(map.get("Up_UserMsg"));
			obj.setUpUserTel(map.get("Up_UserTel"));
			obj.setUpYourNum(map.get("Up_YourNum"));
			objs.add(obj);
			LOGGER.info("接受到短信："+map.get("MOTime")+"&"+map.get("MsgId")+"&"+map.get("Up_UserMsg")+"&"+map.get("Up_UserTel")+"&"+map.get("Up_YourNum"));
		}
		return objs;
	}
	
	public static List<WelinkSmsStatus> smsStatusQuery() throws DocumentException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String postData = "AccountId="+account+"&Password="+password+"&SendedTime="+sdf.format(new Date());
		String postResult = send(postData, smsSendStatusUrl);
		Document document = DocumentHelper.parseText(postResult);
		List<Map<String, String>> list = getElementData(document.getRootElement(), "ReportInfos", "ReportInfo");
		List<WelinkSmsStatus> beans = new ArrayList<WelinkSmsStatus>();
		for (Map<String, String> map : list) {
			WelinkSmsStatus bean = new WelinkSmsStatus();
			bean.setAccountId(map.get("AccountId"));
			bean.setClientMsgId(map.get("ClientMsgId"));
			bean.setMobilePhone(map.get("MobilePhone"));
			bean.setMsgId(map.get("MsgID"));
			bean.setReportResultInfo(map.get("ReportResultInfo"));
			bean.setReportState(map.get("ReportState"));
			bean.setReportTime(map.get("ReportTime"));
			bean.setSendedTime(map.get("SendedTime"));
			bean.setSendResultInfo(map.get("SendResultInfo"));
			bean.setSendState(map.get("SendState"));
			bean.setSpNumber(map.get("SPNumber"));
			beans.add(bean);
		}
		return beans;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, String> getElementData(String content) throws DocumentException {
		Document document = DocumentHelper.parseText(content);
		Element root = document.getRootElement();
		Map<String, String> map = new HashMap<String, String>();
		List<Element> elements = root.elements();
		for (Element element : elements) {
			map.put(element.getName(), element.getTextTrim());
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Map<String, String>> getElementData(Element node, String parentDom, String dom) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Element element = (Element) node.elements(parentDom).get(0);
		List<Element> elements = element.elements(dom);
		for (Element e : elements) {
			List<Element> es = e.elements();
			Map<String, String> map = new HashMap<String, String>();
			for (Element e2 : es) {
				map.put(e2.getName(), e2.getTextTrim());
			}
			list.add(map);
		}
		return list;
	}
	
	static {
		load();
	}
	
	private static void load() {
		Properties p = new Properties();
		InputStream in = WeLinkHelper.class.getResourceAsStream("/config.properties");
		try {
			p.load(in);
			account = p.getProperty("welink.account");
			password = p.getProperty("welink.password");
			prdid = p.getProperty("welink.prdid");
			smsSendUrl = p.getProperty("sms_send_url");
			smsQueryUrl = p.getProperty("sms_query_url");
			smsSendStatusUrl = p.getProperty("sms_send_status_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String send(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	public static void main(String[] args) {
//		try {
//			List<Map<String, String>> maps = smsStatusQuery();
//			System.out.println("反馈信息条数："+maps.size());
//			for (Map<String, String> map : maps) {
//				System.out.println("---------------------------");
//				for (Entry<String, String> entry : map.entrySet()) {
//					System.out.println(entry.getKey()+":"+entry.getValue());
//				}
//			}
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
		try {
			List<WelinkUpSms> list = smsQuery();
			if (list.size()>0) {
				System.out.println("num:"+list.size());
				System.out.println(list.get(0).getUpUserMsg());
			} else {
				System.out.println("没有记录");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}
