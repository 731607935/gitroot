package com.tom.msg.dao;

import com.tom.msg.vo.BindWeixinVo;

/**
 * Created by zhangqiuli on 2016/3/30.
 */
public interface BindweixinDao {

    //新增绑定微信公众号
    public int insertBindWeixin(BindWeixinVo vo);
    
    public BindWeixinVo getBindWeixinVo(String userId);
    
    //修改绑定微信公众号
    public int updateBindFlag(BindWeixinVo vo);
}
