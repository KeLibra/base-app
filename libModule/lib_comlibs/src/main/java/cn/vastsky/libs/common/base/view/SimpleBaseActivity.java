package cn.vastsky.libs.common.base.view;

import cn.vastsky.libs.common.base.impl.IBasePresenter;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/17.
 */
public abstract class SimpleBaseActivity extends IBaseActivity {

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void showEmptyPage(int errorCode) {
        super.showEmptyPage(errorCode);
    }

    @Override
    public void hideEmptyPage() {
        showContent();
    }

}
