package cn.kezy.libs.common.config.userinfo;

import android.content.Context;
import android.os.Bundle;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.StringDef;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class LoginFacade {

    private static Map<String, ILogin> sLoginImlMap = new HashMap<>();
    private static List<LoginListener> sListeners = new ArrayList<>();
    public static final String LOANMARKET_LOGIN = "vast_sky_shop";

    @Retention(SOURCE)
    @StringDef({
            LOANMARKET_LOGIN
    })

    public @interface LOGIN_TYPE {

    }

    public static void addLoginImpl(@LOGIN_TYPE String key, ILogin login) {
        sLoginImlMap.put(key, login);
    }

    private static ILogin getLoginImpl() {
        if (sLoginImlMap.isEmpty()) {
            throw new RuntimeException("you must call addLoginImpl!!");
        }
        ILogin login = sLoginImlMap.get(LOANMARKET_LOGIN);
        if (login != null) {
            return login;
        } else {
            throw new RuntimeException("you must call addLoginImpl!!");
        }
    }

    public static void registerLoginListener(LoginListener listener) {
        synchronized (sListeners) {
            sListeners.add(listener);
        }
    }

    public static void unRegisterLoginListener(LoginListener listener) {
        synchronized (sListeners) {
            sListeners.remove(listener);
        }
    }

    public static List<LoginListener> getLoginListener() {
        return sListeners;
    }

    public static boolean goLoginView(Context context, Bundle bundle, int flags) {
        return getLoginImpl().goLoginView(context, bundle, flags, 0);
    }

    public static boolean goLoginView(Context context) {
        return getLoginImpl().goLoginView(context, null, 0, 0);
    }

    public static boolean goLoginView(Context context, Bundle bundle) {
        return getLoginImpl().goLoginView(context, bundle, 0, 0);
    }

    public static boolean goLoginViewForResult(Context context, Bundle bundle, int flags, int requestCode) {
        return getLoginImpl().goLoginView(context, bundle, flags, requestCode);
    }

    public static boolean logout(Context context) {
        return getLoginImpl().logout(context);
    }

    public static void notifyLoginSucceed(String userId, String token, Object extend) {
        synchronized (sListeners) {
            for (LoginListener sListener : sListeners) {
                sListener.loginSucceed(userId, token, extend);
            }
        }
    }

    public static void notifyLoginFailed(String userId, Throwable e) {
        synchronized (sListeners) {
            for (LoginListener sListener : sListeners) {
                sListener.loginFailed(userId, e);
            }
        }
    }

    public static void notifyLogoutFailed(String userId, Throwable e) {
        synchronized (sListeners) {
            for (LoginListener sListener : sListeners) {
                sListener.logoutFailed(userId, e);
            }
        }
    }

    public static void notifyLogoutSucceed(String userId) {
        synchronized (sListeners) {
            for (LoginListener sListener : sListeners) {
                sListener.logoutSucceed(userId);
            }
        }
    }

    public static void clearUserInfo() {
        LoginFacade.setLogin(false);
        LoginFacade.setToken("");
        LoginFacade.setIsNamed(false);
        LoginFacade.setIdNumber("");
        LoginFacade.setRealName("");
        LoginFacade.setHasPayPwd(false);
        LoginFacade.setMemberSign("");
        LoginFacade.setMemberLevel(0);
        LoginFacade.setUserId(""); //清空userId，base入参不再有手机号，缓存请用getPhone
        LoginFacade.setShareCode("");
        LoginFacade.setThirdBindFlags("");
        LoginFacade.setCardNum("");
        LoginFacade.setPinyin("");
        LoginFacade.setExpireTime("");
        LoginFacade.setNickName("");
        LoginFacade.setCardActiveStatus(0);
        LoginFacade.setCardFaceImage("");
        LoginFacade.setCardFaceHref("");
        LoginFacade.setBtnHref("");
        LoginFacade.setBtnDesc("");
        LoginFacade.setDisplayColor("");
        LoginFacade.setDefaultCardNo("");
        LoginFacade.setCardType(0);
        LoginFacade.setRealIdNumber("");
        LoginFacade.setIdNumberAlias("");
        LoginFacade.setSuperMemberStatus(0);
    }

    public static boolean isLogin() {
        return getLoginImpl().isLogin();
    }

    public static void setLogin(boolean flag) {
        getLoginImpl().setLogin(flag);
    }

    public static String getToken() {
        return getLoginImpl().getToken();
    }

    public static void setToken(String token) {
        getLoginImpl().setToken(token);
    }

    public static String getUserId() {
        return getLoginImpl().getUserId();
    }

    public static void setUserId(String id) {
        getLoginImpl().setUserId(id);
    }

    public static String getPhone() {
        return getLoginImpl().getPhone();
    }

    public static void setPhone(String phone) {
        getLoginImpl().setPhone(phone);
    }

    public static String getRealName() {
        return getLoginImpl().getRealName();
    }

    public static void setRealName(String name) {
        getLoginImpl().setRealName(name);
    }

    public static String getIdNumber() {
        return getLoginImpl().getIdNumber();
    }

    public static void setIdNumber(String idNumber) {
        getLoginImpl().setIdNumber(idNumber);
    }

    public static boolean isNamed() {
        return getLoginImpl().getIsNamed();
    }

    public static void setIsNamed(boolean isNamed) {
        getLoginImpl().setIsNamed(isNamed);
    }

    public static boolean hasPayPwd() {
        return getLoginImpl().getHasPayPwd();
    }

    public static void setHasPayPwd(boolean hasPayPwd) {
        getLoginImpl().setIsPayPwd(hasPayPwd);
    }

    public static String getMemberSign() {
        return getLoginImpl().getMemberSign();
    }

    public static void setMemberSign(String memberSign) {
        getLoginImpl().setMemberSign(memberSign);
    }

    public static int getMemberLevel() {
        return getLoginImpl().getMemberLevel();
    }

    public static void setMemberLevel(int memberLevel) {
        getLoginImpl().setMemberLevel(memberLevel);
    }

    public static String getShareCode() {
        return getLoginImpl().getShareCode();
    }

    public static void setShareCode(String shareCode) {
        getLoginImpl().setShareCode(shareCode);
    }

    public static String getThidBindFlags() {
        return getLoginImpl().getThirdBindFlags();
    }

    public static void setThirdBindFlags(String flags) {
        getLoginImpl().setThirdBindFlags(flags);
    }

    public static String getCardNum() {
        return getLoginImpl().getCardNum();
    }

    public static void setCardNum(String cardNum) {
        getLoginImpl().setCardNum(cardNum);
    }

    public static String getPinyin() {
        return getLoginImpl().getPinyin();
    }

    public static void setPinyin(String pinyin) {
        getLoginImpl().setPinyin(pinyin);
    }

    public static String getExpireTime() {
        return getLoginImpl().getExpireTime();
    }

    public static void setExpireTime(String expireTime) {
        getLoginImpl().setExpireTime(expireTime);
    }

    public static boolean getHasSetLoginPasswd() {
        return getLoginImpl().getHasSetLoginPasswd();
    }

    public static void setHasSetLoginPasswd(boolean hasSetLoginPasswd) {
        getLoginImpl().setHasSetLoginPasswd(hasSetLoginPasswd);
    }

    public static String getMemberId() {
        return getLoginImpl().getMemberId();
    }

    public static void setmemberId(String memberId) {
        getLoginImpl().setMemberId(memberId);
    }

    public static String getNickName() {
        return getLoginImpl().getNickName();
    }

    public static void setNickName(String nickName) {
        getLoginImpl().setNickName(nickName);
    }

    public static int getCardActiveStatus() {
        return getLoginImpl().getCardActiveStatus();
    }

    public static void setCardActiveStatus(int cardActiveStatus) {
        getLoginImpl().setCardActiveStatus(cardActiveStatus);
    }

    public static String getCardFaceImage() {
        return getLoginImpl().getCardFaceImage();
    }

    public static void setCardFaceImage(String cardFaceImage) {
        getLoginImpl().setCardFaceImage(cardFaceImage);
    }

    public static String getCardFaceHref() {
        return getLoginImpl().getCardFaceHref();
    }

    public static void setCardFaceHref(String cardFaceHref) {
        getLoginImpl().setCardFaceHref(cardFaceHref);
    }

    public static String getBtnHref() {
        return getLoginImpl().getBtnHref();
    }

    public static void setBtnHref(String btnHref) {
        getLoginImpl().setBtnHref(btnHref);
    }

    public static String getBtnDesc() {
        return getLoginImpl().getBtnDesc();
    }

    public static void setBtnDesc(String btnDesc) {
        getLoginImpl().setBtnDesc(btnDesc);
    }

    public static String getDisplayColor() {
        return getLoginImpl().getDisplayColor();
    }

    public static void setDisplayColor(String displayColor) {
        getLoginImpl().setDisplayColor(displayColor);
    }

    public static String getDefaultCardNo() {
        return getLoginImpl().getDefaultCardNo();
    }

    public static void setDefaultCardNo(String defaultCardNo) {
        getLoginImpl().setDefaultCardNo(defaultCardNo);
    }

    public static String getRealIdNumber() {
        return getLoginImpl().getRealIdNumber();
    }

    public static void setRealIdNumber(String idNumber) {
        getLoginImpl().setRealIdNumber(idNumber);
    }

    public static int getCardType() {
        return getLoginImpl().getCardType();
    }

    public static void setCardType(int type) {
        getLoginImpl().setCardType(type);
    }

    public static String getIdNumberAlias() {
        return getLoginImpl().getIdNumberAlias();
    }

    public static void setIdNumberAlias(String idNumberAlias) {
        getLoginImpl().setIdNumberAlias(idNumberAlias);
    }

    public static int getIdType() {
        return getLoginImpl().getIdType();
    }

    public static void setIdType(int type) {
        getLoginImpl().setIdType(type);
    }

    public static int getSuperMemberStatus() {
        return getLoginImpl().getSuperMemberStatus();
    }

    public static void setSuperMemberStatus(int status) {
        getLoginImpl().setSuperMemberStatus(status);
    }


}
