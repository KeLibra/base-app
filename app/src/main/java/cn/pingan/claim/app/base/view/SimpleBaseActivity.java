package cn.pingan.claim.app.base.view;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/17.
 */
public abstract class SimpleBaseActivity extends IBaseActivity {


    @Override
    public void showEmptyPage(int errorCode) {
        super.showEmptyPage(errorCode);
    }

    @Override
    public void hideEmptyPage() {
        showContent();
    }

}
