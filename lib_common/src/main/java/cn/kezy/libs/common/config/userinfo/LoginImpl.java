package cn.kezy.libs.common.config.userinfo;

import android.content.Context;
import android.os.Bundle;

import cn.kezy.libs.common.store.SharedPrefStore;


/**
 * @author: kezy
 * @create_time 2019/10/11
 * @copyright blackfish
 * @description:
 */
public class LoginImpl implements ILogin {

    private static final String MEMBER_PREF_NAME = "member_store"; //会员相关数据
    private static final String LOGIN_KEY = "is_login";
    private static final String TOKEN_KEY = "token";
    private static final String USER_ID_KEY = "key_user_id";
    private static final String PHONE_KEY = "user_phone_no";
    private static final String REAL_NAME = "real_name";
    private static final String ID_NUMBER = "id_number";
    private static final String IS_NAMED = "is_named";
    private static final String HAS_PAYPWD = "has_paypwd";
    private static final String MEMBER_SIGN = "member_sign";
    private static final String MEMBER_LEVEL = "member_level";
    private static final String SHARE_CODE = "share_code";
    private static final String THIRD_BIND_FLAGS = "third_bind_flags";

    private static final String CARD_NUM = "card_num";
    private static final String PINYIN = "pinyin";
    private static final String EXPIRETIME = "expiretime";
    private static final String HASSETLOGINPASSWD = "hassetloginpasswd";
    private static final String MEMBER_ID = "MEMBER_ID";
    private static final String NICK_NAME = "NICK_NAME";

    private static final String CARD_ACTIVE_STATUS = "cardActiveStatus";
    private static final String CARD_FACE_IMAGE = "cardFaceImage";
    private static final String CARD_FACE_HREF = "cardFaceHref";
    private static final String BTN_HREF = "btnHref";
    private static final String BTN_DESC = "btnDesc";
    private static final String DISPLAY_COLOR = "displayColor";
    private static final String DEFAULT_CARDNO = "getDefaultCardNo";
    private static final String REAL_ID_NUMBER = "realIdNumber";
    private static final String CARD_TYPE = "cardType";
    private static final String ID_NUMBER_ALIAS = "idNumberAlias";
    private static final String ID_TYPE = "idType";
    private static final String SUPER_MEMBER_STATUS = "superMemberStatus";

    private class MemberSPStore extends SharedPrefStore {
        MemberSPStore(Context context) {
            super(context, MEMBER_PREF_NAME);
        }
    }

    private static MemberSPStore store = null;

    public LoginImpl(Context context) {
        store = new MemberSPStore(context);
    }

    @Override
    public boolean isLogin() {
        return store.getBoolean(LOGIN_KEY, false);
    }

    @Override
    public void setLogin(boolean b) {
        store.putBoolean(LOGIN_KEY, b);
    }

    @Override
    public String getToken() {
        return (store.getString(TOKEN_KEY, ""));
    }

    @Override
    public void setToken(String token) {
        store.putString(TOKEN_KEY, (token));
    }

    @Override
    public void setUserId(String s) {
        store.putString(USER_ID_KEY, (s));
    }

    @Override
    public void setPhone(String s) {
        store.putString(PHONE_KEY, (s));
    }

    @Override
    public String getPhone() {
        return (store.getString(PHONE_KEY, ""));
    }

    @Override
    public String getUserId() {
        return (store.getString(USER_ID_KEY, ""));
    }

    @Override
    public boolean goLoginView(Context context, Bundle bundle, int i, int request) {

        return true;
    }

    @Override
    public boolean logout(Context context) {
        return false;
    }

    @Override
    public String getRealName() {
        return store.getString(REAL_NAME, "");
    }

    @Override
    public void setRealName(String s) {
        store.putString(REAL_NAME, s);
    }

    @Override
    public String getIdNumber() {
        return store.getString(ID_NUMBER, "");
    }

    @Override
    public void setIdNumber(String s) {
        store.putString(ID_NUMBER, s);
    }

    @Override
    public boolean getIsNamed() {
        return store.getBoolean(IS_NAMED, false);
    }

    @Override
    public void setIsNamed(boolean b) {
        store.putBoolean(IS_NAMED, b);
    }

    @Override
    public boolean getHasPayPwd() {
        return store.getBoolean(HAS_PAYPWD, false);
    }

    @Override
    public void setIsPayPwd(boolean b) {
        store.putBoolean(HAS_PAYPWD, b);
    }

    @Override
    public String getMemberSign() {
        return store.getString(MEMBER_SIGN, "");
    }

    @Override
    public void setMemberSign(String s) {
        store.putString(MEMBER_SIGN, s);
    }

    @Override
    public int getMemberLevel() {
        return store.getInt(MEMBER_LEVEL, 0);
    }

    @Override
    public void setMemberLevel(int i) {
        store.putInt(MEMBER_LEVEL, i);
    }

    @Override
    public String getShareCode() {
        return store.getString(SHARE_CODE, "");
    }

    @Override
    public void setShareCode(String s) {
        store.putString(SHARE_CODE, s);
    }

    @Override
    public String getThirdBindFlags() {
        return store.getString(THIRD_BIND_FLAGS, "");
    }

    @Override
    public void setThirdBindFlags(String s) {
        store.putString(THIRD_BIND_FLAGS, s);
    }

    @Override
    public String getCardNum() {
        return store.getString(CARD_NUM, "");
    }

    @Override
    public void setCardNum(String s) {
        store.putString(CARD_NUM, s);
    }

    @Override
    public String getPinyin() {
        return store.getString(PINYIN, "");
    }

    @Override
    public void setPinyin(String s) {
        store.putString(PINYIN, s);
    }

    @Override
    public String getExpireTime() {
        return store.getString(EXPIRETIME, "");
    }

    @Override
    public void setExpireTime(String s) {
        store.putString(EXPIRETIME, s);
    }

    @Override
    public boolean getHasSetLoginPasswd() {
        return store.getBoolean(HASSETLOGINPASSWD, false);
    }

    @Override
    public void setHasSetLoginPasswd(boolean s) {
        store.putBoolean(HASSETLOGINPASSWD, s);
    }

    @Override
    public String getMemberId() {
        return store.getString(MEMBER_ID, "");
    }

    @Override
    public void setMemberId(String memberId) {
        store.putString(MEMBER_ID, memberId);
    }

    @Override
    public String getNickName() {
        return store.getString(NICK_NAME, "");
    }

    @Override
    public void setNickName(String s) {
        store.putString(NICK_NAME, s);
    }

    @Override
    public int getCardActiveStatus() {
        return store.getInt(CARD_ACTIVE_STATUS, 0);
    }

    @Override
    public void setCardActiveStatus(int i) {
        store.putInt(CARD_ACTIVE_STATUS, i);
    }

    @Override
    public String getCardFaceImage() {
        return store.getString(CARD_FACE_IMAGE, "");
    }

    @Override
    public void setCardFaceImage(String s) {
        store.putString(CARD_FACE_IMAGE, s);
    }

    @Override
    public String getCardFaceHref() {
        return store.getString(CARD_FACE_HREF, "");
    }

    @Override
    public void setCardFaceHref(String s) {
        store.putString(CARD_FACE_HREF, s);
    }

    @Override
    public String getBtnHref() {
        return store.getString(BTN_HREF, "");
    }

    @Override
    public void setBtnHref(String s) {
        store.putString(BTN_HREF, s);
    }

    @Override
    public String getBtnDesc() {
        return store.getString(BTN_DESC, "");
    }

    @Override
    public void setBtnDesc(String s) {
        store.putString(BTN_DESC, s);
    }

    @Override
    public String getDisplayColor() {
        return store.getString(DISPLAY_COLOR, "");
    }

    @Override
    public void setDisplayColor(String s) {
        store.putString(DISPLAY_COLOR, s);
    }

    @Override
    public String getDefaultCardNo() {
        return store.getString(DEFAULT_CARDNO, "");
    }

    @Override
    public void setDefaultCardNo(String s) {
        store.putString(DEFAULT_CARDNO, s);
    }

    @Override
    public String getRealIdNumber() {
        return store.getString(REAL_ID_NUMBER, "");
    }

    @Override
    public void setRealIdNumber(String idNumber) {
        store.putString(REAL_ID_NUMBER, idNumber);
    }

    @Override
    public int getCardType() {
        return store.getInt(CARD_TYPE, 0);
    }

    @Override
    public void setCardType(int type) {
        store.putInt(CARD_TYPE, type);
    }

    @Override
    public String getIdNumberAlias() {
        return store.getString(ID_NUMBER_ALIAS, "");
    }

    @Override
    public void setIdNumberAlias(String idNumberAlias) {
        store.putString(ID_NUMBER_ALIAS, idNumberAlias);
    }

    @Override
    public int getIdType() {
        return store.getInt(ID_TYPE, 0);
    }

    @Override
    public void setIdType(int type) {
        store.putInt(ID_TYPE, type);
    }

    @Override
    public int getSuperMemberStatus() {
        return store.getInt(SUPER_MEMBER_STATUS, 0);
    }

    @Override
    public void setSuperMemberStatus(int status) {
        store.putInt(SUPER_MEMBER_STATUS, status);
    }

    public static void updateLoginStatus(LoginUserBean output, String phoneNum) {
        LoginFacade.setLogin(true);
        LoginFacade.setUserId(phoneNum);
        LoginFacade.setPhone(phoneNum);
        if (output != null) {
            LoginFacade.setToken(output.token);
            LoginFacade.setHasPayPwd(output.hasSetPayPassword);
            LoginFacade.setRealName(output.realName);
            LoginFacade.setIdNumber(output.idNumberAlias);
            LoginFacade.setIsNamed(output.idCardFlag == 1);
            LoginFacade.setMemberSign(output.memberSign);
            LoginFacade.setShareCode(output.shareCode);
            LoginFacade.setThirdBindFlags(output.thirdBindFlags);
            LoginFacade.setmemberId(output.aliasName); //更新memberId
            LoginFacade.setNickName(output.nickName);
        }
    }
}
