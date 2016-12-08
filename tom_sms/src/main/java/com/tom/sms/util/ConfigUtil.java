package com.tom.sms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 配置工具类   获取配置信息,来获取指定文件内的内容
 * @author zcp
 *
 */
public class ConfigUtil {

	private static Log log = LogFactory.getLog(ConfigUtil.class);
	//需要解析的properties文件
	private static String propertiesUrl = "config.properties";
	
	private static Properties prop = null;
	
	
	static{
		init();
	}
	
	private static void init(){
		prop = new Properties();
		InputStream in = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesUrl);
			prop.load(in);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}  finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
					in = null;
				}
			}
		}
		
	}
	
	/**
	 * 获得String类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		String value = prop.getProperty(key);
		return value==null?null:value;
	}
	
	/**
	 * 获得int类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key){
		String value = prop.getProperty(key);
		return value==null?null: Integer.parseInt(prop.getProperty(key));
	}
	
	/**
	 * 获得boolean类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		String value = prop.getProperty(key);
		return value==null?null: Boolean.valueOf(prop.getProperty(key));
	}
	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(ConfigUtil.getString("sms_send_url"));
	}
}	
