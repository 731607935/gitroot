package com.tom.msg.service;

import java.util.List;
import java.util.Map;

import com.tom.msg.vo.BindWeixinVo;

/**
 * Created by zhangqiuli on 2016/3/30.
 */
public interface BindWeixinService {

    /**
     * 绑定微信公众号
     * @param vo
     * @return
     */
    public int addBindWeixn(BindWeixinVo vo);
    
    /**
     * 判断用户是否绑定微信公众号
     * @param userName
     * @return
     */
    public String bindFlag(String userName);
    
    /**
     * 修改绑定微信公众号
     * @param userName
     * @param bind_weixin
     * @return
     */
    public int updateBindFlag(String userName,int bind_weixin);
    
    /**
     * 微信推送消息
     * @param url
     * @param mailList
     * @return
     */
    public void sendMailMsg(String url,List<Map<String,String>> mailList);
}
