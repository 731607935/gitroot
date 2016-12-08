package com.tom.sms.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 常量工具类
 * @author zcp
 *
 */
public class ConstantUtils {

	private static Map<String, String> constantMap = new HashMap<String, String>();
	
	private static final String CONFIG = "config.properties";
	
	static {
		load(CONFIG);
	}
	
	private static void load(String fileName) {
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		Enumeration<String> en = rb.getKeys();
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			try {
				constantMap.put(key, new String(rb.getString(key).getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从配置文件中根据key获取value，如果找不到对应value，返回null
	 * @param key
	 * @return
	 */
	public static String getConstantValue(String key) {
		if (constantMap.containsKey(key))
			return constantMap.get(key);
		return null;
	}
	
}
