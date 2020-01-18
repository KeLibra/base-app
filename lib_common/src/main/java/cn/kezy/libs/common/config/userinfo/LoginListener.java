package cn.kezy.libs.common.config.userinfo;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public interface LoginListener {

    void loginSucceed(String userId, String token, Object extend);

    void loginFailed(String userId, Throwable e);

    void logoutSucceed(String userId);

    void logoutFailed(String usedId, Throwable e);
}
