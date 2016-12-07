package com.tom.msg.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tom.msg.service.WechatService;
import com.tom.msg.vo.WchatMessageVo;


public class AcceptMailJob {

	private Log log = LogFactory.getLog(AcceptMailJob.class);
	
	@Autowired
	private WechatService wechatService;
	
	private static String sendWxMsgUrl = ConfigUtil.getString("weixin.sendurl");
	private static String sendWarnMailUrl = ConfigUtil.getString("weixin.sendwarnurl");
	
	
	/**
	 * 业务逻辑处理
	 */
	public void doWork(){
		long startTime = System.currentTimeMillis();
		log.info("我是Job每5分钟执行一次,重新推送消息 "+new Date(startTime));
		List<WchatMessageVo> messageList = new ArrayList<WchatMessageVo>();
		Map<String,String> msgMap = new HashMap<String,String>();
		try {
			messageList = wechatService.getMessageList();
			if(!messageList.isEmpty()){
				//MessageUtil.httpConnection(sendWarnMailUrl);
				sendWarnMail(messageList.size());
				for (WchatMessageVo wchatMessageVo : messageList) {
					msgMap.put("touser", wchatMessageVo.getTouser());
					msgMap.put("fromuser", wchatMessageVo.getFromuser());
					msgMap.put("subject", wchatMessageVo.getSubject());
					msgMap.put("message_id", wchatMessageVo.getMessage_id());
					String result = null;
					try {
						result = HttpXmlClient.post(sendWxMsgUrl, msgMap);
					} catch (Exception e) {
						log.error(wchatMessageVo.getTouser()+"WeChat PHP接受随身邮消息未通"+result);
						log.error(e.getMessage());
						break;
					}
					log.info(wchatMessageVo.getTouser()+"WeChat PHP重推结果"+result);
					if(result.indexOf("000")!=-1 || result.indexOf("002")!=-1 || result.indexOf("004")!=-1){
						log.info(wchatMessageVo.getTouser()+"#WeChat PHP接受随身邮消息已通");
						wechatService.delMessage(wchatMessageVo.getId());
					}else{
						log.info(wchatMessageVo.getTouser()+"#WeChat PHP接受随身邮消息未通");
						break;
					}
				}
			}else{
				log.info("WeChat 此时没有需要重推的随身邮消息");
			}
			
		} catch (Exception e) {
			log.error("我是Job每5分钟执行一次,查询重推随身邮失败 "+new Date());
			log.error(e.getMessage());
			e.printStackTrace();
		}finally {
			messageList = null;
			msgMap = null;
			log.info("Message_push 重推随身邮 UsedTime: "
					+ (System.currentTimeMillis() - startTime));
		}
	}
	
	public void sendWarnMail(int size){
    	long start = System.currentTimeMillis();
    	log.info("WeChat #接到报警指令");
    	List<String> toUserList = new ArrayList<String>();
    	try {
    		String subject = "随身邮推送失败";
    		String userName = "tommailnotify@tom.com";
    		String pwd = "tom@tom.com";
    		String mailContent = "主人,随身邮接受推送消息有异常,"+size+"条记录推送失败，请及时处理.";
    		toUserList.add("zhaochangpan@tomonline-inc.com");
    		toUserList.add("hanyongchu@tomonline-inc.com");
    		toUserList.add("wanhua@tomonline-inc.com");
    		toUserList.add("xiexianbo@tomonline-inc.com");
    		for (String toUser : toUserList) {
				SendMailByContentUtil.SendMailByOther(subject, userName, pwd, toUser, mailContent);
			}
		}finally{
			toUserList = null;
			log.info("UsedTime: imap " + (System.currentTimeMillis() - start));
		}
    }
}
