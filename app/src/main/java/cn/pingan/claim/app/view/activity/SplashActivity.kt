package cn.pingan.claim.app.view.activity;

import android.content.Intent
import cn.kezy.libs.common.utils.PermissionUtils
import cn.pingan.claim.app.R
import cn.pingan.claim.app.base.view.SimpleBaseActivity
import kotlinx.android.synthetic.main.activity_splash.*


/**
 *
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 * 闪屏页面
 */

class SplashActivity : SimpleBaseActivity() {

    override fun getContentLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        requestPermissions()
    }


    fun requestPermissions() {
        PermissionUtils.checkPermission(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION), object : PermissionUtils.OnPermissionRequestListener {
            override fun onPermissionRequest(granted: Boolean, var2: String?) {
                initConfig()
            }

            override fun onPermissionRequest(isAllGranted: Boolean, var2: Array<out String>, var3: IntArray?) {
                initConfig()
            }
        })
    }


    override fun loadData() {
    }

    private fun initLocationInfo() {

    }

    fun initConfig() {

        initLocationInfo()

        rl_splash.postDelayed(Runnable {
            startActivity(Intent(this, MainActivity().javaClass))
            finish()
        }, 1500)
    }

    override fun hasTitle(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.dispatchPermissionResult(this, requestCode, permissions, grantResults, null)
    }

}
 