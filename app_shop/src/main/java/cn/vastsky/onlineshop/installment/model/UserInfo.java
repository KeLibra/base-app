package cn.vastsky.onlineshop.installment.model;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * 用户信息
 */
public class UserInfo extends BaseBean {
    public String gender;//": null,
    public String name;//": null,
    public String headUrl;//": null,
    public String phoneNo;//": "13700000000",
    public boolean isLocked;//": false,
    public int regist;//": 1    //0：未注册、1：已注册、2：小黑鱼会员
    public String jwt;
    public String lmToken;
    //用户类型：0-新用户 1-老用户
    public String userType;
}
