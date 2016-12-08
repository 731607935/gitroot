package com.tom.msg.vo;

import java.util.Date;

/**
 * Created by zhangqiuli on 2016/3/30.
 */
public class BindWeixinVo {

    //自增id
    private int id;
    //用户名
    private String userId;
    //是否绑定微信 1：绑定 0：未绑定
    private int bind_weixin;
    //插入时间
    private Date create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBind_weixin() {
        return bind_weixin;
    }

    public void setBind_weixin(int bind_weixin) {
        this.bind_weixin = bind_weixin;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
