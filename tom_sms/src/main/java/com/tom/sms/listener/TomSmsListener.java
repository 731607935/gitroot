package com.tom.sms.listener;

import com.tom.sms.web.StartUp;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TomSmsListener
  implements ServletContextListener
{
  private static Log log = LogFactory.getLog(TomSmsListener.class);

  public void contextDestroyed(ServletContextEvent arg0)
  {
    log.info("我要停止读取短信le.....");
  }

  public void contextInitialized(ServletContextEvent arg0)
  {
    log.info("我要开始读取短信le.....");
    StartUp.startTomSms();
  }
}