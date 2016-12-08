package com.tom.msg.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.msg.service.BindWeixinService;
import com.tom.msg.vo.BindWeixinVo;

/**
 * 与webmail交互,处理微信绑定和推送消息的控制类
 * @author z
 *
 */
@Controller
@RequestMapping("/weixin/")
public class MessageController{
	private static Logger log = Logger.getLogger(MessageController.class);
	
	@Autowired
	private BindWeixinService bindWeixinService;
	
	/**
	 * 用户绑定微信随身邮入库操纵
	 * @param request
	 * @return 001 该绑定成功     004 绑定失败
	 */
	@RequestMapping(value="createBind")
	@ResponseBody
	public String createBind(HttpServletRequest request){
		long start = System.currentTimeMillis();
		String userName = request.getParameter("userName");
		String type = request.getParameter("type");
		log.info("绑定用户随身邮请求参数"+userName+","+type);
		String returnCode = null;
		if(type!=null && type.equals("update")){//未绑定状态0改成绑定状态1
			int result = bindWeixinService.updateBindFlag(userName,1);
			if(result>0){
				log.info(userName+"修改用户绑定关系成功");
				returnCode = "001";
			}else{
				log.info(userName+"修改用户绑定关系失败");
				returnCode = "004";
			}
		}
		if(type!=null && type.equals("add")){//新增用户绑定关系
			BindWeixinVo bwxv = new BindWeixinVo();
			bwxv.setUserId(userName);
			bwxv.setBind_weixin(1);
			int result = bindWeixinService.addBindWeixn(bwxv);
			if(result>0){
				log.info(userName+"新增用户绑定关系成功");
				returnCode = "001";
			}else{
				log.info(userName+"新增用户绑定关系失败");
				returnCode = "004";
			}
		}
		if(type!=null && type.equals("del")){//解除用户绑定关系
			int result = bindWeixinService.updateBindFlag(userName,0);
			if(result>0){
				log.info(userName+"解除用户绑定关系成功");
				returnCode = "001";
			}else{
				log.info(userName+"解除用户绑定关系失败");
				returnCode = "004";
			}
		}
		log.info(userName+"###绑定用户关系UsedTime:" + (System.currentTimeMillis() - start));
		return returnCode;
	}
	
	/**
	 * 查询用户是否已经绑定了微信随身邮
	 * @param request
	 * @return  000 用户已经存在且已经绑定   001 该用户已经存在但没绑定   003 用户不存在
	 */
	@RequestMapping(value="getBind")
	@ResponseBody
	public String getBind(HttpServletRequest request){
		long start = System.currentTimeMillis();
		String userName = request.getParameter("userName");
		log.info(userName+"查询该用户是否绑定了随身邮");
		//msg： 000 用户已经存在且已经绑定   001 该用户已经存在但没绑定   003 用户不存在
		String msg = bindWeixinService.bindFlag(userName);
		log.info(userName+"###用户查询绑定关系为"+msg+",UsedTime:" + (System.currentTimeMillis() - start));
		return msg;
	}
	

}
