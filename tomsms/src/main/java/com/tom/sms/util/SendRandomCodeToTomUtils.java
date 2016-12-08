package com.tom.sms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tom.sms.module.WelinkUpSms;

public class SendRandomCodeToTomUtils {

	private static String URL = ConstantUtils.getConstantValue("tom.url");
	private static Logger log = Logger.getLogger("tom");
	
	@SuppressWarnings("unchecked")
	public static void send(List<WelinkUpSms> beans) {
		if (null != beans && beans.size() > 0) {
			log.info("start send user random code to TOM , total size:"+beans.size());
			for (WelinkUpSms bean : beans) {
				log.info("==>requestURL:"+URL+", params:mobile["+bean.getUpUserTel()+"];content["+bean.getUpUserMsg()+"];sendTime["+bean.getMoTime()+"]");
				String content = bean.getUpUserMsg();
				if (!content.toUpperCase().startsWith("ZC")) {
					log.info("====>content isn't match, pass...");
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("phone", bean.getUpUserTel());
				map.put("volicode", content);
				map.put("sendTime", bean.getMoTime());
				String result = HttpHelper.process(URL, map);
				log.info("====>Tom return code : " + result);
				Map<String, String> resultMap = (Map<String, String>) JSON.parse(result);
				String returnCode = resultMap.get("code");
				if (null == returnCode || "".equals(returnCode) || !"001".equals(returnCode))
					log.error("====>falue to send ! " + resultMap.get("codeMsg"));
			}
		}
		log.info("======>send random code to TOM end ....");
	}
	
	public static void main(String[] args) {
//		YunxinDeliver[] beans = new YunxinDeliver[1];
//		YunxinDeliver bean = new YunxinDeliver();
//		bean.setContent("TOM0002，83727");
//		bean.setMobile("13482372854");
//		bean.setTime("2016/6/16 21:23:12");
//		beans[0] = bean;
//		send(beans);
	}
}
