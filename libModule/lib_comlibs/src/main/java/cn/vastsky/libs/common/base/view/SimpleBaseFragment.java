package cn.vastsky.libs.common.base.view;


import cn.vastsky.libs.common.base.impl.IBasePresenter;

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
