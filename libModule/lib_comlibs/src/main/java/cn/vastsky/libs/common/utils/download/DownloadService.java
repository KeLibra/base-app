package cn.vastsky.libs.common.utils.download;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.vastsky.lib.base.common.config.AppConfigLib;
import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.libs.common.R;

/**
 * @author: kezy
 * @create_time 2019/11/28
 * @description:
 */
public class DownloadService extends IntentService {

    private static final String TAG = "DownloadService";
    public static final String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.example.android.threadsample.STATUS";
    private LocalBroadcastManager mLocalBroadcastManager;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //获取下载地址
        String url = intent.getDataString();
        LogUtils.d(TAG, url);
        //获取DownloadManager对象
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定APK缓存路径和应用名称,比如我这个路径就是/storage/emulated/0/Download/vooloc.apk
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "bianlifenqi.apk");
        //设置网络下载环境为wifi或者移动数据
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置显示通知栏，下载完成后通知栏自动消失
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //设置通知栏标题
        request.setTitle(getResources().getString(R.string.app_name));
        //设置这个下载的描述，显示在通知中
        request.setDescription("正在下载...");
        //设置类型为.apk
        request.setMimeType("application/vnd.android.package-archive");
        //获得唯一下载id
        long requestId = downloadManager.enqueue(request);
        LogUtils.d("-------------------------  " + requestId);
        //将id放进Intent
//        Intent localIntent = new Intent(BROADCAST_ACTION);
//        localIntent.putExtra(EXTENDED_DATA_STATUS, requestId);
//        localIntent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID, requestId);
        //查询下载信息
        DownloadManager.Query query = new DownloadManager.Query();
        //只包括带有给定id的下载。
        query.setFilterById(requestId);
        try {
            boolean isGoging = true;
            while (isGoging) {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        //如果下载状态为成功
                        case DownloadManager.STATUS_SUCCESSFUL:
                            isGoging = false;
                            LogUtils.d("--------------------------------Download success " + requestId);

                            installApk(getAppFileUri(requestId));
                            break;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri getAppFileUri(long requestId) {
        LogUtils.d("--------------------------------Download getAppFileUri " + requestId);
        DownloadManager dManager = (DownloadManager) getBaseContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(requestId);
        LogUtils.d("--------------------------------Download success downloadFileUri " + downloadFileUri);

        LogUtils.d("======msg_zxl", "uri-1:" + downloadFileUri);
        if (ContentResolver.SCHEME_FILE.equals(downloadFileUri.getScheme())
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FileUri - Convert it to contentUri.
            LogUtils.e("======msg_zxl", "uri.getPath():" + downloadFileUri.getPath());
            if (downloadFileUri.getPath() != null) {
                File file = new File(downloadFileUri.getPath());
                LogUtils.d("======msg_zxl", "file:" + file);
                downloadFileUri = FileProvider.getUriForFile(getBaseContext(), AppConfigLib.getContext().getPackageName(), file);
            }
        }
        LogUtils.d("======msg_zxl", "downloadFileUri:" + downloadFileUri);

        return downloadFileUri;
    }


    void installApk(final Uri uri) {
        LogUtils.d("======msg_zxl", "downloadFileUri:" + uri);
        if (uri == null) {
            Toast toast = Toast.makeText(AppConfigLib.getContext(), "安装失败，请稍后重试", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        Intent install = new Intent();
        install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        getBaseContext().startActivity(install);
    }
}
