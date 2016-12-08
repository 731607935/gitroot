package com.tom.sms.util;

import com.tom.sms.timer.WelinkUpSmsSender;
import com.tom.sms.web.StartUp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AcceptMailJob{
  private static Log log = LogFactory.getLog(AcceptMailJob.class);

  public static void doWork(){
    log.info("读短信线程最后运行时间 = " + WelinkUpSmsSender.lastRunTime);
    if (!DateUtil.countTime(WelinkUpSmsSender.lastRunTime, DateUtil.getNowDate())) {
      log.info("读取短信线程停了哦...");
      StartUp.startTomSms();
    }else {
      log.info("正常运行读取短信");
    }
  }
}