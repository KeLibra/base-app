package cn.vastsky.lib.base.common.view.impl;

import android.view.View;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public interface IErrorPageView {

    interface OnRefreshListener {
        void onRefreshBtnClick();
    }

    View getView();

    void setOnRefreshListener(OnRefreshListener listener);

    void networkErrorOrNot(int restErrorCode);

    void report();

    void retry();

    void showErrorMsg(int errCode, String errMsg);
}
