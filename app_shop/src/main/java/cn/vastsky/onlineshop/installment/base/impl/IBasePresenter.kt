package cn.vastsky.onlineshop.installment.base.impl


/**
 */
interface IBasePresenter<View> {
    val isViewAttached: Boolean
    val view: View?
    fun attachView(view: View)
    fun detachView()
}
