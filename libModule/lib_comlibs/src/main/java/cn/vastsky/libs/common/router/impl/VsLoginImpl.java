package cn.vastsky.libs.common.router.impl;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import cn.vastsky.lib.base.common.config.userinfo.LoginFacade;
import cn.vastsky.lib.base.common.config.userinfo.LoginImpl;
import cn.vastsky.lib.base.common.store.SharedPrefStore;
import cn.vastsky.lib.utils.ToastUtil;
import cn.vastsky.libs.common.model.UserInfo;


/**
 * 用户信息报存
 */
public class VsLoginImpl extends LoginImpl {
    /**
     *
     */
    private static final String MEMBER_PREF_NAME = "vs_user_store";
    private static MemberSPStore store = null;

    public VsLoginImpl(Context context) {
        super(context);
        store = new MemberSPStore(context);
    }

    public static void clearLoginInfo() {
        if (store == null) {
            return;
        }
        store.putObject("userInfo", null);
    }

    public static boolean setUserInfo(UserInfo userInfo) {
        if (store == null) {
            return false;
        }
        return store.putObject("userInfo", userInfo);
    }

    public static UserInfo getUserInfo() {
        if (store == null) {
            return null;
        }
        UserInfo userInfo = store.getObject("userInfo", UserInfo.class);
        return userInfo == null ? new UserInfo() : userInfo;
    }

    public static void updateLoginStatus(UserInfo output, String phoneNum) {
        LoginFacade.setLogin(true);
        LoginFacade.setUserId(phoneNum);
        LoginFacade.setPhone(phoneNum);
        if (output != null) {
            setUserInfo(output);
        }
    }

    private class MemberSPStore extends SharedPrefStore {
        MemberSPStore(Context context) {
            super(context, MEMBER_PREF_NAME);
        }
    }

    @Override
    public boolean goLoginView(Context context, Bundle bundle, int i, int request) {
        if (!(context instanceof FragmentActivity)) {
            return false;
        } else {
            ToastUtil.show("登录操作");

            return true;
        }
    }

    @Override
    public boolean logout(Context context) {
        LoginFacade.clearUserInfo();
        return super.logout(context);

    }
}
