package com.tom.sms.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpHelper {

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String process(String url, Map<String, String> params) {
		String result = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			HttpPost httpPost = new HttpPost(url + "?z" + new Date().getTime());

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
				String paramName = (String) entry.getKey();
				String paramValue = (String) entry.getValue();
				nvps.add(new BasicNameValuePair(paramName, paramValue));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				int s = response.getStatusLine().getStatusCode();
				result = EntityUtils.toString(entity);
			}
			if (entity != null) {
				entity.consumeContent();
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
