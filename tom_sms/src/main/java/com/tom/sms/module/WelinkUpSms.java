package com.tom.sms.module;

import java.io.Serializable;

public class WelinkUpSms
  implements Serializable
{
  private static final long serialVersionUID = 6578470190111152732L;
  private String msgId;
  private String upYourNum;
  private String upUserTel;
  private String upUserMsg;
  private String moTime;

  public String getMsgId()
  {
    return this.msgId;
  }
  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }
  public String getUpYourNum() {
    return this.upYourNum;
  }
  public void setUpYourNum(String upYourNum) {
    this.upYourNum = upYourNum;
  }
  public String getUpUserTel() {
    return this.upUserTel;
  }
  public void setUpUserTel(String upUserTel) {
    this.upUserTel = upUserTel;
  }
  public String getUpUserMsg() {
    return this.upUserMsg;
  }
  public void setUpUserMsg(String upUserMsg) {
    this.upUserMsg = upUserMsg;
  }
  public String getMoTime() {
    return this.moTime;
  }
  public void setMoTime(String moTime) {
    this.moTime = moTime;
  }
}