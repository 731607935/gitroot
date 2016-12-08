package com.tom.sms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static String defaultFormat = "yyyy-MM-dd HH:mm:ss";
  public static DateFormat df;

  public static String getNowDate()
  {
    df = new SimpleDateFormat(defaultFormat);
    return df.format(new Date());
  }

  public static boolean countTime(String begin, String end)
  {
    int hour = 0;
    int minute = 0;
    long total_minute = 0L;
    boolean result = false;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date begin_date = df.parse(begin);
      Date end_date = df.parse(end);

      total_minute = (end_date.getTime() - begin_date.getTime()) / 60000L;

      hour = (int)total_minute / 60;
      minute = (int)total_minute % 60;
    }
    catch (ParseException e) {
      System.out.println("传入的时间格式不符合规定");
      return result;
    }
    if ((hour == 0) && (minute <= 1)) {
      result = true;
    }
    return result;
  }
  public static void main(String[] args) {
    System.out.println("和当前时间相差是否超过一分钟：" + countTime("2016-07-15 21:26:42", getNowDate()));
  }
}