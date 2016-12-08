package com.tom.msg.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tom.msg.dao.BindweixinDao;
import com.tom.msg.service.BindWeixinService;
import com.tom.msg.util.HttpXmlClient;
import com.tom.msg.vo.BindWeixinVo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangqiuli on 2016/3/30.
 */
@Service
public class BindWeixinServiceImpl implements BindWeixinService {
	
	private Log log = LogFactory.getLog(BindWeixinServiceImpl.class);
	
    @Autowired
    private BindweixinDao bindweixinDao;
    @Override
    public int addBindWeixn(BindWeixinVo vo) {
        return bindweixinDao.insertBindWeixin(vo);
    }
	@Override
	public String bindFlag(String userName) {
		BindWeixinVo bwx = bindweixinDao.getBindWeixinVo(userName);
		if(bwx!=null){
			if(bwx.getBind_weixin()==1){
				return "000";//已经绑定成功
			}else{
				return "001";//用户存在，但是没有绑定
			}
		}else{
			return "003";//用户不存在
		} 
	}
	
	@Override
	public int updateBindFlag(String userName,int bind_weixin) {
		BindWeixinVo vo = new BindWeixinVo();
		vo.setBind_weixin(bind_weixin);
		vo.setUserId(userName);
		return bindweixinDao.updateBindFlag(vo);
	}
	
	public void sendMailMsg(String url, List<Map<String, String>> mailList) {
		long startTime = System.currentTimeMillis();
		Map<String,String> msgMap = new HashMap<String,String>();
		try{
		for (Map<String, String> mailMap : mailList) {
			try {
				msgMap.put("touser", mailMap.get("to_real"));
				msgMap.put("fromuser",mailMap.get("from"));
				msgMap.put("subject", URLDecoder.decode(mailMap.get("subject"),"utf-8"));
				msgMap.put("time", mailMap.get("sendDate"));
				msgMap.put("message_id", mailMap.get("msg_id"));
				String xml = HttpXmlClient.post(url, msgMap);
				log.info("请求返回数据"+xml+"subject:"+URLDecoder.decode(mailMap.get("subject"),"utf-8"));
				} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				}
			}
		}finally{
			msgMap = null;
			mailList=null;
			log.info("##Message_push##请求发送post数据UsedTime:" + (System.currentTimeMillis() - startTime));
		}
	}
}
