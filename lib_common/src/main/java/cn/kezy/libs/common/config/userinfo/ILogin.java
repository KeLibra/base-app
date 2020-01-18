package cn.kezy.libs.common.config.userinfo;

import android.content.Context;
import android.os.Bundle;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description: 定义login行为
 */
public interface ILogin {


    boolean isLogin();

    void setLogin(boolean login);

    String getToken();

    void setToken(String token);

    void setUserId(String userId);

    void setPhone(String phone);

    String getPhone();

    String getUserId();

    boolean goLoginView(Context context, Bundle bundle, int flags, int request);

    boolean logout(Context context);

    String getRealName();

    void setRealName(String name);

    String getIdNumber();

    void setIdNumber(String idNumber);

    boolean getIsNamed();

    void setIsNamed(boolean isNamed);

    boolean getHasPayPwd();

    void setIsPayPwd(boolean isPayPwd);

    String getMemberSign();

    void setMemberSign(String memberSign);

    int getMemberLevel();

    void setMemberLevel(int memberLevel);

    String getShareCode(); //分享码 @add 1.9.0

    void setShareCode(String shareCode);

    String getThirdBindFlags(); //第三方绑定情况

    void setThirdBindFlags(String flags);

    String getCardNum(); //卡号

    void setCardNum(String cardNum);

    String getPinyin(); //姓名拼音

    void setPinyin(String pinyin);

    String getExpireTime();

    void setExpireTime(String expireTime);

    boolean getHasSetLoginPasswd();

    void setHasSetLoginPasswd(boolean hasSetLoginPasswd);

    String getMemberId();

    void setMemberId(String memberId);

    String getNickName();

    void setNickName(String nickName);

    int getCardActiveStatus();

    void setCardActiveStatus(int cardActiveStatus);

    String getCardFaceImage();

    void setCardFaceImage(String cardFaceImage);

    String getCardFaceHref();

    void setCardFaceHref(String cardFaceHref);

    String getBtnHref();

    void setBtnHref(String btnHref);

    String getBtnDesc();

    void setBtnDesc(String btnDesc);

    String getDisplayColor();

    void setDisplayColor(String displayColor);

    String getDefaultCardNo();

    void setDefaultCardNo(String defaultCardNo);

    String getRealIdNumber();

    void setRealIdNumber(String idNumber);

    int getCardType();

    void setCardType(int type);

    String getIdNumberAlias();

    void setIdNumberAlias(String idNumberAlias);

    int getIdType();

    void setIdType(int type);

    int getSuperMemberStatus();

    void setSuperMemberStatus(int status);
}
