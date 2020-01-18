package cn.kezy.libs.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by airal on 2018/10/9.
 */

public class PermissionUtils {
    private static final String TAG = "====msg_" + PermissionUtils.class.getSimpleName();
    private static int sIncRequestCode = 0;
    private static Map<Integer, OnPermissionRequestListener> mListenerMap = new HashMap();

    public PermissionUtils() {
    }

    public static void dispatchPermissionResult(Activity activity, int requestCode, String[] permissions, int[] grantResults, PermissionUtils.onPermisssionDeniedListener dlistener) {
        if (permissions.length != 0 && grantResults.length != 0) {
            for (int index = 0; index < permissions.length; ++index) {
                LogUtils.d(TAG, "request {}, {}" + permissions[index] + " " + (grantResults[index] == 0));
                if (grantResults[index] != 0 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[index]) && dlistener != null) {
                    dlistener.onPermissonDenied(activity, permissions[index]);
                }
            }

            PermissionUtils.OnPermissionRequestListener listener = (PermissionUtils.OnPermissionRequestListener) mListenerMap.get(Integer.valueOf(requestCode));
            if (listener != null) {
                if (permissions.length == 1) {
                    listener.onPermissionRequest(grantResults[0] == 0, permissions[0]);
                } else {
                    boolean isAllGranted = true;
                    int[] var7 = grantResults;
                    int var8 = grantResults.length;

                    for (int var9 = 0; var9 < var8; ++var9) {
                        int grant = var7[var9];
                        if (grant != 0) {
                            isAllGranted = false;
                            listener.onPermissionRequest(false, permissions, grantResults);
                            break;
                        }
                    }

                    if (isAllGranted) {
                        listener.onPermissionRequest(true, permissions, grantResults);
                    }
                }

                mListenerMap.remove(Integer.valueOf(requestCode));
            }

        }
    }

    public static void checkPermission(Activity activity, String permission, PermissionUtils.OnPermissionRequestListener listener) {
        checkPermission(activity, new String[]{permission}, listener);
    }

    public static void checkPermission(Activity activity, String[] permissions, PermissionUtils.OnPermissionRequestListener listener) {
        if (activity != null && permissions != null && permissions.length > 0) {
            List<String> unauthorizedPermissions = new ArrayList();
            String[] var4 = permissions;
            int var5 = permissions.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String permission = var4[var6];
                if (!TextUtils.isEmpty(permission)) {
                    try {
                        if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
                            LogUtils.d(TAG, "request {}" + permission);
                            unauthorizedPermissions.add(permission);
                        } else {
                            LogUtils.d(TAG, "already has {} permission" + permission);
                        }
                    } catch (Exception var9) {
                        LogUtils.d(TAG, "check self permission failed. {}" + var9.toString());
                        unauthorizedPermissions.add(permission);
                    }
                }
            }

            if (!unauthorizedPermissions.isEmpty()) {
                ++sIncRequestCode;
                if (sIncRequestCode > 255) {
                    sIncRequestCode = 0;
                }

                mListenerMap.put(Integer.valueOf(sIncRequestCode), listener);
                ActivityCompat.requestPermissions(activity, (String[]) unauthorizedPermissions.toArray(new String[unauthorizedPermissions.size()]), sIncRequestCode);
            } else if (listener != null) {
                if (permissions.length == 1) {
                    listener.onPermissionRequest(true, permissions[0]);
                } else {
                    listener.onPermissionRequest(true, permissions, (int[]) null);
                }
            }

        }
    }

    public static boolean hasGrantedPermission(Context activity, String permission) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else if (activity != null && !TextUtils.isEmpty(permission)) {
            if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
                LogUtils.d(TAG, "request {}" + permission);
                return false;
            } else {
                LogUtils.d(TAG, "already has {} permission" + permission);
                return true;
            }
        } else {
            LogUtils.e(TAG, "activity or permission is empty");
            return false;
        }
    }

    public static boolean permissionIsRefuseAndHide(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) != 0 && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

//    public static void showGrantToast(Context context, String unauthorizedPermission) {
//        if(!TextUtils.isEmpty(unauthorizedPermission)) {
//            if("android.permission.CAMERA".equals(unauthorizedPermission)) {
//                Toast.makeText(context, string.lib_grant_permission_camera);
//            } else if("android.permission.ACCESS_COARSE_LOCATION".equals(unauthorizedPermission)) {
//                Toast.makeText(context, string.lib_grant_permission_location);
//            }
//
//        }
//    }
//
//    public static void showGrantLocationToast(Context context) {
//        Toast.makeText(context, string.lib_grant_permission_location);
//    }

    public abstract static class DefaultPermissionRequest implements PermissionUtils.OnPermissionRequestListener {
        public DefaultPermissionRequest() {
        }


        @Override
        public void onPermissionRequest(boolean granted, String permission) {
        }


        @Override
        public void onPermissionRequest(boolean isAllGranted, String[] permissions, int[] grantResults) {
        }
    }

    public interface OnPermissionRequestListener {
        void onPermissionRequest(boolean granted, String var2);

        void onPermissionRequest(boolean isAllGranted, String[] var2, int[] var3);
    }

    public interface onPermisssionDeniedListener {
        void onPermissonDenied(Context var1, String var2);
    }
}

