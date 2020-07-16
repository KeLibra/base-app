package cn.vastsky.onlineshop.installment.base.view;


import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;

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
