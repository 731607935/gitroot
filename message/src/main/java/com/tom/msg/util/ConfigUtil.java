package com.tom.msg.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 配置工具类
 * 
 * @author yvon
 *
 */
public class ConfigUtil {
	
	protected static Logger log = Logger.getLogger(ConfigUtil.class);
	
	private static Configuration config = null;
	
	private static Map<String,String> folderViewNameMap = new HashMap<String,String>();
	
	static{
		init();
	}
	
	/**
	 * 初始化
	 */
	private static void init(){
		InputStream in = null;
		try {
			Properties p = new Properties();
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties");
			p.load(in);
			config = new XMLConfiguration(p.getProperty("config.locaction"));
			initViewNameMap();
		} catch (ConfigurationException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally{
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
	
	private static void initViewNameMap(){
		Iterator it = config.getKeys("imap-folder-mapping");
		String str = null;
		for(;it.hasNext();){
			str = it.next().toString();
			folderViewNameMap.put(config.getString(str), str.split("\\.")[1]);
		}
		str = null;
	}
	
	/**
	 * 获得String类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		return config==null?null:config.getString(key);
	}
	
	/**
	 * 获得int类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key){
		return config==null?null:config.getInt(key);
	}
	

	/**
	 * 获得boolean类型的属性值
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return config==null?null:config.getBoolean(key);
	}
	
	/**
	 * 根据IMAP中的文件夹名获得程序中的显示名
	 * 
	 * @param folderName
	 * @return
	 */
	public static String getFolderViewName(String folderName){
		String result = folderViewNameMap.get(folderName);
		if(result==null){
			try {
				return encodeForJs(URLEncoder.encode(folderName,"UTF-8")).trim();
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
				return folderName;
			}
		}else{
			return result;
		}
	}
	
	/**
	 * 根据程序中的显示名获得IMAP中的文件夹名
	 * 
	 * @param viewName
	 * @return
	 */
	public static String getFolderImapName(String viewName){
		String result = getString("imap-folder-mapping."+viewName);
		if(result==null){
			try {
				return URLDecoder.decode(decodeForJs(viewName),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(),e);
				return viewName;
			}
		}else{
			return result;
		}
	}
	
	/**
	 * 根据IMAP中的文件夹名获得显示名字
	 * 
	 * @param viewName
	 * @return
	 */
	public static String getFolderDisplayName(String viewName){
		return getString("display-folder-mapping."+viewName);
	}
	
	/**
	 * jQuery表达式中Id不能有特殊符号
	 * 在尽量不大范围改动前端的前提下，为了让前端能方便直接用folderName做Id属性
	 * 这里对%进行自定义转义
	 * 
	 * @return
	 */
	private static String encodeForJs(String str){
		if(str.indexOf("%")!=-1){
			return str.replaceAll("%", "_-");
		}
		return str;
	}
	
	private static String decodeForJs(String str){
		if(str.indexOf("_-")!=-1){
			return str.replaceAll("_-", "%");
		}
		return str;
	}
}
