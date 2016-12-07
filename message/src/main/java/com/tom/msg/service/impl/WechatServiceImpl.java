package com.tom.msg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.msg.dao.WeChatDao;
import com.tom.msg.service.WechatService;
import com.tom.msg.vo.WchatMessageVo;

@Service
public class WechatServiceImpl implements WechatService {
	
	@Autowired
	private WeChatDao weChatDao;
	
	@Override
	public List<WchatMessageVo> getMessageList() {
		// TODO Auto-generated method stub
		return weChatDao.getMessageList();
	}

	@Override
	public int delMessage(int id) {
		// TODO Auto-generated method stub
		return weChatDao.delMessage(id);
	}

}
