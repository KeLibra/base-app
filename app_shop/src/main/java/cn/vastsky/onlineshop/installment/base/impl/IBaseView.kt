package cn.vastsky.onlineshop.installment.base.impl

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity

interface IBaseView {
    val context: Context

    /**
     * @return
     */
    val activity: FragmentActivity

    fun showToast(msg: String)
    fun showLoading(msg: String)
    fun hideLoading()
    fun _getIntent(): Intent


    fun dofinish()
    fun showEmptyPage(errorCode: Int)
    fun hideEmptyPage()
}
