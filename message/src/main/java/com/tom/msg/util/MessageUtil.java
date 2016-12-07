package com.tom.msg.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageUtil {

	private static Log log = LogFactory.getLog(MessageUtil.class);
	//static String urlStr = "http://a.mail.tom.com:6060/webmail/pay/sendQuery.action?authProfile=wuwei&p2_Order=yeepay_88370123";
	
	public static String httpConnection(String urlStr) throws Exception {
		long start = System.currentTimeMillis();
		URL url;
		String sTotalString = "";
		InputStream urlStream =null;
		BufferedReader bufferedReader=null;
		try {
			log.info("connect URL: " + urlStr);
			url = new URL(urlStr);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			// 设置连接主机超时
			httpConnection.setConnectTimeout(15000);
			// 设置从主机读取数据超时
			httpConnection.setReadTimeout(15000);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpConnection.setRequestProperty("contentType", "utf-8");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {

				urlStream = httpConnection.getInputStream();
				bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream, "UTF-8"));
				String sCurrentLine = "";

				while ((sCurrentLine = bufferedReader.readLine()) != null) {
					sTotalString += sCurrentLine;
				}
				log.info("REQUEST RETURN : " + sTotalString);
				System.out.println("###################"+sTotalString.length());
				System.out.println("*******************"+sTotalString);
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
			}else{
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
				log.error("##推送调用webmail取信接口#" + url + "#返回链接信息为:"
						+ responseCode);
			}
		} finally {
			if(urlStream != null){
				urlStream.close();
			}
			if(bufferedReader != null){
				bufferedReader.close();
			}
			log.info(urlStr + " UsedTime: "
					+ (System.currentTimeMillis() - start));
		}
		return sTotalString;
	}
}
