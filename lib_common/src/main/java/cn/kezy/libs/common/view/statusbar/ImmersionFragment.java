package cn.kezy.libs.common.view.statusbar;

import androidx.fragment.app.Fragment;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class ImmersionFragment extends Fragment {

    private ImmersionBar mImmersionBar;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed() && immersionEnabled()) {
            if (mImmersionBar == null) {
                mImmersionBar = ImmersionBar.with(this);
            }
            immersionInit(mImmersionBar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && immersionEnabled()) {
            if (mImmersionBar == null) {
                mImmersionBar = ImmersionBar.with(this);
            }
            immersionInit(mImmersionBar);
        }
    }

    /**
     * 当前页面Fragment支持沉浸式初始化。子类可以重写返回false，设置不支持沉浸式初始化
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean immersionEnabled() {
        return true;
    }

    protected ImmersionBar immersionInit(ImmersionBar mImmersionBar) {
        return mImmersionBar;
    }

}
