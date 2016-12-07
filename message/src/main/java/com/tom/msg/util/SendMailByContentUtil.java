package com.tom.msg.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SendMailByContentUtil {

    private static Log log = LogFactory.getLog(SendMailByContentUtil.class);

    /**
     *
     * @param subject 邮件主题
     * @param userName 发信人账号
     * @param pwd 发信人密码
     * @param toUser  收信人
     * @param mailContent  邮件内容
     * @param messageBodyPart  是否含有附件
     */
    public static void SendMailByOther(String subject, String userName, String pwd, String toUser, String mailContent){
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "false");
        Session session = Session.getInstance(props,null);
        Message msg = new MimeMessage(session);
        String subjectStr = subject;
        String fromStr = userName;
        String password = pwd;
        String toStr = toUser;//"163vip@163.net"
        log.info("发信人："+fromStr+"发信人密码"+password+"收信人："+toStr);
        try {
            msg.setSubject(subjectStr);
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(fromStr));
            log.info("发送邮件地址："+fromStr);
            Address address = new InternetAddress(toStr);
            Address[] addesses = {address};
            msg.setReplyTo(addesses);
            Transport transport = session.getTransport("smtp");
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(mailContent);
            String sendIP = "172.24.171.252";
            int sendPort = 25;
            transport.connect(sendIP, sendPort, fromStr, password);//"172.24.173.7", 20022
            MimeMultipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(textBodyPart);
            msg.setContent(multiPart);
            //收件人
            Address addressTo = new InternetAddress(toStr);
            msg.addRecipient(Message.RecipientType.TO, addressTo);
            transport.sendMessage(msg, msg.getAllRecipients());
            log.info("邮件发送成功");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
