package cn.pingan.claim.app.base.common.utils.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * @author: kezy
 * @create_time 2019/11/26
 * @description:
 */

public class UpdataBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "bianlifenqi.apk");
        //获取权限
        try {
            Runtime.getRuntime().exec("chmod 777" + file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent = new Intent(Intent.ACTION_VIEW);
        //如果设置，这个活动将成为这个历史堆栈上的新任务的开始
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //7.0以上的版本
            Uri apkUri = FileProvider.getUriForFile(context, "cn.vastsky.onlineshop.installment.provider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            //7.0以下的版本
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
