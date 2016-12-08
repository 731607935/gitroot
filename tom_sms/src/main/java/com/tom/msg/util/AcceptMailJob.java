package com.tom.msg.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AcceptMailJob {

	private Log log = LogFactory.getLog(AcceptMailJob.class);
	
	private static String getMailListUrl = ConfigUtil.getString("tomsms");
	
	public static void main(String[] args) {
		doWork();
	}
	/**
	 * 业务逻辑处理
	 */
	public static void doWork(){
		System.out.println("拿信去喽。。。。");
		System.out.println(getMailListUrl);
	}
}
