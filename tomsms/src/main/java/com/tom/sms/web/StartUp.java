package com.tom.sms.web;

import com.tom.msg.util.ConfigUtil;
import com.tom.sms.timer.WelinkUpSmsSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartUp{
  private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(Integer.valueOf(ConfigUtil.getString("system.thread")));

  public static void startTomSms(){
    if (Boolean.valueOf(ConfigUtil.getString("tom.upsms.isSend")))
      EXECUTOR.scheduleWithFixedDelay(new WelinkUpSmsSender(), 0, Integer.valueOf(ConfigUtil.getString("tom.upsms.timegap")), TimeUnit.MILLISECONDS);
  }
  
}