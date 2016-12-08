package com.tom.sms.timer;

import com.tom.sms.module.WeLinkHelper;
import com.tom.sms.module.WelinkUpSms;
import com.tom.sms.util.DateUtil;
import com.tom.sms.util.SendRandomCodeToTomUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

public class WelinkUpSmsSender implements Runnable{
  private static final Logger LOGGER = Logger.getLogger(WelinkUpSmsSender.class);
  public static String lastRunTime;

  public void run(){
    lastRunTime = DateUtil.getNowDate();
    try {
      List<WelinkUpSms> list = WeLinkHelper.smsQuery();
      if (list.size() > 0) {
        LOGGER.info("Totally got " + list.size() + " UpSms，start processing ...");
        SendRandomCodeToTomUtils.send(list);
        LOGGER.info("Processing end ...");
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }
}