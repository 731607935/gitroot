package com.tom.msg.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tom.msg.dao.WeChatDao;
import com.tom.msg.vo.WchatMessageVo;

@Repository
public class WeChatDaoImpl extends BaseDaoSupport<WchatMessageVo> implements WeChatDao{

	@Override
	public List<WchatMessageVo> getMessageList() {
		// TODO Auto-generated method stub
		List<WchatMessageVo> list = new ArrayList<WchatMessageVo>();
		list = this.getListByStatementName("WeChatMessage.getMessageList", null);
		return list;
	}

	@Override
	public int delMessage(int id) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("WeChatMessage.delMessage", Long.parseLong(String.valueOf(id)));
	}

}
