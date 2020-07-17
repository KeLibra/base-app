package test.kezy.com.lib_commom.base.view;

import test.kezy.com.lib_commom.base.impl.IBasePresenter;

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
