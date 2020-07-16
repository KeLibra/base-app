package cn.vastsky.onlineshop.installment.common.utils.download;

import android.content.Context;

import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.common.dialog.AppUploadDialog;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.model.bean.response.AppUpdateResponse;
import cn.vastsky.onlineshop.installment.service.UserModel;

/**
 * @author: kezy
 * @create_time 2019/12/2
 * @description:
 */
public class AppUpLoadUtils {

    public static void appUpLoad(Context context, boolean isShowToast) {
        UserModel.checkUpdate(new ICallBack<AppUpdateResponse>() {
            @Override
            public void onSuccess(AppUpdateResponse response) {
                if (response != null && response.optional != 3) {
                    AppUploadDialog dialog = new AppUploadDialog(context, response);
                    dialog.show();
                } else {
                    if (isShowToast) {
                        ToastUtil.show("已经是最新版本了");
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                if (isShowToast) {
                    ToastUtil.show("已经是最新版本了");
                }
            }
        });
    }
}
