package test.kezy.com.lib_commom.utils.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import androidx.core.content.FileProvider;

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



//    public void onReceive(Context context, Intent intent) {
//
//        LogUtils.e("--------------------------------------------------");
//
//        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//        long refernece = Store.sp(AppConfigLib.getContext()).getLong("app_download_refernece", 0);
//        if (refernece != myDwonloadID) {
//            return;
//        }
//
//        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
//        installAPK(context, downloadFileUri);
//    }
//
//    private void installAPK(Context context, Uri apk) {
//        if (Build.VERSION.SDK_INT < 23) {
//            Intent intents = new Intent();
//            intents.setAction("android.intent.action.VIEW");
//            intents.addCategory("android.intent.category.DEFAULT");
//            intents.setType("application/vnd.android.package-archive");
//            intents.setData(apk);
//            intents.setDataAndType(apk, "application/vnd.android.package-archive");
//            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intents);
//        } else {
//            File file = queryDownloadedApk(context);
//            if (file.exists()) {
//                openFile(file, context);
//            }
//        }
//    }
//
//    /**
//     * 通过downLoadId查询下载的apk，解决6.0以后安装的问题
//     *
//     * @param context
//     * @return
//     */
//    public static File queryDownloadedApk(Context context) {
//        File targetApkFile = null;
//        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        long downloadId = Store.sp(AppConfigLib.getContext()).getLong("app_download_refernece", 0);
//        if (downloadId != -1) {
//            DownloadManager.Query query = new DownloadManager.Query();
//            query.setFilterById(downloadId);
//            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
//            Cursor cur = downloader.query(query);
//            if (cur != null) {
//                if (cur.moveToFirst()) {
//                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                    if (!TextUtils.isEmpty(uriString)) {
//                        targetApkFile = new File(Uri.parse(uriString).getPath());
//                    }
//                }
//                cur.close();
//            }
//        }
//        return targetApkFile;
//
//    }
//
//    private void openFile(File file, Context context) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction("android.intent.action.VIEW");
//        String type = getMIMEType(file);
//        Uri apkUri = FileProvider.getUriForFile(context, AppConfigLib.getContext().getPackageName() + ".provider", file);
////        intent.setDataAndType(Uri.fromFile(file), type);
//        intent.setDataAndType(apkUri, type);
//        try {
//            context.startActivity(intent);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private String getMIMEType(File var0) {
//        String var1 = "";
//        String var2 = var0.getName();
//        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
//        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
//        return var1;
//    }
}
