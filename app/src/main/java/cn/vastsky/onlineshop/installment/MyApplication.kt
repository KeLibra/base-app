package cn.vastsky.onlineshop.installment

import android.content.pm.PackageManager
import android.text.TextUtils
import androidx.multidex.MultiDexApplication
import cn.vastsky.lib.base.common.config.AppConfigLib
import cn.vastsky.lib.base.common.config.userinfo.LoginFacade
import cn.vastsky.lib.router.PageRouter
import cn.vastsky.libs.common.VsConstants
import cn.vastsky.libs.common.config.UrlConfig
import cn.vastsky.libs.common.router.WebHandler
import cn.vastsky.libs.common.router.impl.VsLoginImpl
import cn.vastsky.libs.gdlocation.LocationManagerGaode
import cn.vastsky.libs.gdlocation.config.LocationConfig
import cn.vastsky.libs.gdlocation.manager.LocationManager
import com.mcxiaoke.packer.helper.PackerNg


/**
 */
class MyApplication : MultiDexApplication() {

    private var channelId: String = ""

    override fun onCreate() {
        super.onCreate()

        // 高德地图
        LocationManager.getInstance().init(LocationManagerGaode())
        LocationConfig.init(this)

        AppConfigLib.init(this)
        var channel = PackerNg.getChannel(applicationContext)
        if (TextUtils.isEmpty(channel)) {
            channel = "default"
        }

        AppConfigLib.setChannel(channel)

        initPageRouter()
        channelId = channel
        initNetWorkEnv()

        initScreenInfo()

        initRouterAndImpl()

    }

    private fun initPageRouter() {

        PageRouter.register(WebHandler())
    }

    private fun initNetWorkEnv() {
        if (BuildConfig.DEBUG) {
            UrlConfig.setEnv(VsConstants.NetWork.SIT)
        }
    }

    private fun initRouterAndImpl() {
        LoginFacade.addLoginImpl(LoginFacade.LOANMARKET_LOGIN, VsLoginImpl(this))

        val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)

    }

    private fun initScreenInfo() {
    }
}
