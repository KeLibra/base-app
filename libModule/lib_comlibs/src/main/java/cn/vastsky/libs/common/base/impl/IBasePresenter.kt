package cn.vastsky.libs.common.base.impl


/**
 */
interface IBasePresenter<View> {
    val isViewAttached: Boolean
    val view: View?
    fun attachView(view: View)
    fun detachView()
}
