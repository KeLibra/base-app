package cn.vastsky.onlineshop.installment.base.view;

import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;

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
