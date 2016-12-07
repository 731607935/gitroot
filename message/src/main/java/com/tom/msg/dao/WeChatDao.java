package com.tom.msg.dao;

import java.util.List;

import com.tom.msg.vo.WchatMessageVo;

public interface WeChatDao {

	public List<WchatMessageVo> getMessageList();
	
	public int delMessage(int id);
}
