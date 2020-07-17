package test.kezy.com.lib_commom.base


import test.kezy.com.lib_commom.base.impl.IBasePresenter
import test.kezy.com.lib_commom.base.impl.IBaseView
import java.lang.ref.WeakReference

/**
 *
 */
abstract class BasePresenter<V : IBaseView>
/**创建presenter的时候加载view */
(view: V) : IBasePresenter<V> {

    /**当前presenter所持有的视图对象 */
    private var viewReference: WeakReference<V>? = null

    /**
     * 判断view是否可用
     */
    override val isViewAttached: Boolean
        get() = viewReference != null && viewReference!!.get() != null

    override val view: V?
        get() = if (viewReference == null) {
            null
        } else viewReference!!.get()

    init {
        attachView(view)
    }

    /**绑定view对象 供presenter使用 */
    override fun attachView(v: V) {
        viewReference = WeakReference(v)
        onAttach()
    }

    /**解除view的引用 防止持有对象导致内存泄露 */
    override fun detachView() {
        if (viewReference != null) {
            viewReference!!.clear()
            viewReference = null
        }
        onDetach()
    }

    protected fun showLoading(msg: String) {
        if (view != null) {
            view!!.showLoading(msg)
        }
    }

    protected fun hideLoading() {
        if (view != null) {
            view!!.hideLoading()
        }
    }

    protected fun showToast(msg: String) {
        if (view != null) {
            view!!.showToast(msg)
        }
    }

    protected fun onDetach() {

    }

    protected fun onAttach() {

    }
}
