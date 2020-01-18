package cn.kezy.libs.common.config.userinfo;


import cn.kezy.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class LoginUserBean extends BaseBean {
    public static final int APP_HOME_PAGE = 1; //app首页
    public int loginStatus;  //登录状态 1:登录; 0:未登录
    public String token; //后续用于校验用户登录状态【算法：MD5(phoneNum+termId+salt1)】
    public String isNewComer; //是否为第一次注册 1:是; 0:否
    public String aliasName;//别名

    public String avatarUrl; // 头像
    public int idCardFlag; //0、未实名，1、已实名
    public String userName; //13812345678(String-非空-手机号码)",
    public String realName; // "张三(String-可空-真实姓名)",
    public String idNumberAlias; //320321199001019999(String-可空-身份证号码)"
    public boolean hasSetPayPassword; // true已经设置付款密码 false 未设置密码
    public String memberSign; //会员唯一标示
    public int nextPageCode; //1:首页;其他:成功页
    public String shareCode; //会员分享码
    public String thirdBindFlags; //第三方绑定标记 0101...010001 倒数第1位：QQ
    public String nickName; //昵称
}
