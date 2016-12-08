package com.tom.msg.dao.impl;

import com.tom.msg.dao.BindweixinDao;
import com.tom.msg.vo.BindWeixinVo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangqiuli on 2016/3/30.
 */
@Repository
public class BindWeixinDaoImpl extends BaseDaoSupport<BindWeixinVo> implements BindweixinDao {
    @Override
    public int insertBindWeixin(BindWeixinVo vo) {
        return (Integer) this.insert("BindWeixin.addBindWeixin",vo);
    }
    
	@Override
	public BindWeixinVo getBindWeixinVo(String userId) {
		return this.findByParameter("BindWeixin.getBind",userId);
	}

	@Override
	public int updateBindFlag(BindWeixinVo vo) {
		return update("BindWeixin.updateBind",vo);
	}
}
