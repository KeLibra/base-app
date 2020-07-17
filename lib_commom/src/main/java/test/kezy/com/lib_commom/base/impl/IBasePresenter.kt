package test.kezy.com.lib_commom.base.impl


/**
 */
interface IBasePresenter<View> {
    val isViewAttached: Boolean
    val view: View?
    fun attachView(view: View)
    fun detachView()
}
