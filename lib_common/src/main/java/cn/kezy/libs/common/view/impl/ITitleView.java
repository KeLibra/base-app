package cn.kezy.libs.common.view.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 *
 * 标题栏接口, 默认实现为DefaultTitleView,
 *  * 如ui变更, 在lib.ui中实现
 */
public interface ITitleView {

    void attachTo(ViewGroup parent);

    /**
     * 获取标题栏
     *
     * @return 标题栏控件
     */
    @NonNull
    View getView();

    /**
     * 获取标题
     *
     * @return 文字控件
     */
    TextView getTextView();

    /**
     * 获取返回按钮
     *
     * @return 返回控件
     */
    View getBackView();

    /**
     * 获取刷新按钮
     *
     * @return 刷新控件
     */
    View getRefreshView();

    /**
     * 获取分享按钮
     *
     * @return 分享控件
     */
    View getShareView();

    /**
     * 获取关闭按钮
     *
     * @return 关闭控件
     */
    View getCloseView();

    /**
     * 是否写死标题
     *
     * @return
     */
    boolean getFixedTitle();

}
