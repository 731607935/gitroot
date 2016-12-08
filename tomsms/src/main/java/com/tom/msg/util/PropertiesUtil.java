package com.tom.msg.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置工具类   获取配置信息,可以更换配置文件的路径名字，来获取不同文件内的内容
 * @author zhaochangpan
 *
 */
public class PropertiesUtil {

	public static Properties loadProperties(String properties) throws Exception {
		Properties prop = new Properties();
		InputStream propertiesStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(properties);
		try {
			prop.load(propertiesStream);
		} finally {
			propertiesStream.close();
		}
		return prop;
	}
	
	
	public static <T> T getPara(Properties properties, Class<T> type, String key) {
		String value = properties.getProperty(key);
		return parseValue(type, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T parseValue(Class<T> type, String value) {
		if (Integer.class == type || int.class == type) {
			return (T) Integer.valueOf(value);
		}
		if (Double.class == type || double.class == type) {
			return (T) Double.valueOf(value);
		}
		if (Long.class == type || long.class == type) {
			return (T) Long.valueOf(value);
		}
		if (Boolean.class == type || boolean.class == type) {
			return (T) Boolean.valueOf(value);
		}
		return (T) value;
	}
	
	public static void main(String[] args) throws Exception {
		Properties properties = loadProperties("config.properties");
		System.out.println(getPara(properties, boolean.class, "si.printsql"));//false
		System.out.println(getPara(properties, String.class, "si.page.pagersize"));//10
	}
}
