package cn.vastsky.onlineshop.installment.common.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.view.dialog.CommonDialog;
import cn.vastsky.onlineshop.installment.common.utils.download.DownloadService;
import cn.vastsky.onlineshop.installment.model.bean.response.AppUpdateResponse;

/**
 * @author: kezy
 * @create_time 2019/11/29
 * @description:
 */
public class AppUploadDialog {

    Context context;

    CommonDialog dialog;

    public AppUploadDialog(Context context, AppUpdateResponse response) {
        LogUtils.e("------------------------------- AppUploadDialog");
        this.context = context;
        if (response == null || response.optional == 3) {
            ToastUtil.show("已经是最新版本了");
            return;
        } else {
            initDialog(response);
        }
    }

    public void initDialog(AppUpdateResponse response) {
        LogUtils.e("------------------------------- initDialog");
        String apkUrl = response.downloadUrl;

        if (dialog == null) {
            dialog = new CommonDialog(context);
        }
        dialog.setMessage(response.info);
        if (response.optional == 1) {
            // 强制更新
            dialog.setSingleBtnTextWithListener("立即更新", new CommonDialog.onSingleBtnListener() {
                @Override
                public void onClick() {
                    startDownLoadApk(apkUrl);
                }
            });
        }
        if (response.optional == 2) {
            // 选择更新
            dialog.setPositive("立即更新");
            dialog.setNegtive("取消");


            dialog.setOnClickBtnListener(new CommonDialog.OnClickBtnListener() {
                @Override
                public void onPositiveClick() {
                    startDownLoadApk(apkUrl);
                }

                @Override
                public void onNegtiveClick() {
                    dialog.dismiss();
                }
            });
        }
    }

    private void startDownLoadApk(String apkUrl) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setData(Uri.parse(apkUrl));
        context.startService(intent);
    }

    public void show() {
        LogUtils.e("------------------------------- show() " + dialog);
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

}
