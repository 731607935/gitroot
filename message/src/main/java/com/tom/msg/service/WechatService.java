package com.tom.msg.service;

import java.util.List;

import com.tom.msg.vo.WchatMessageVo;

public interface WechatService {

	public List<WchatMessageVo> getMessageList();
	
	public int delMessage(int id);
}
