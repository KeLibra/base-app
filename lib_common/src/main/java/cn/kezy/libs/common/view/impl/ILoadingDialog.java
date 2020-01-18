package cn.kezy.libs.common.view.impl;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public interface ILoadingDialog {
    /**
     * 点击外部区域时(包括后退按钮)是否可以关闭弹窗
     *
     * @return 可以关闭则返回true, 否则返回false
     */
    boolean cancellableOnTouchOutside();

    boolean isShowing();

    void show();

    void dismiss();
}
