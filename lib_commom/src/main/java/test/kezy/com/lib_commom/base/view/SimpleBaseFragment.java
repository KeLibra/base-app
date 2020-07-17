package test.kezy.com.lib_commom.base.view;


import test.kezy.com.lib_commom.base.impl.IBasePresenter;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/17.
 */
public abstract class SimpleBaseFragment extends IBaseFragment {

	@Override
	protected IBasePresenter getPresenter() {
		return null;
	}

}
