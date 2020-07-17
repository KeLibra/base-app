package cn.vastsky.libs.gdlocation.utils;

import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.vastsky.lib.base.common.BaseHandler;
import cn.vastsky.lib.base.common.config.AppConfigLib;
import cn.vastsky.lib.utils.LogUtils;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class GaodeCodeHelper {
    public static final String CITY_FILE_NAME = "city.json";
    public static final String FILE_AMP_JSON = "amap_code.json";
    private static final int MESSAGE_GET_CODE = 1;
    private static final int MESSAGE_GET_SHORT_NAME = 2;
    private Handler mHandler;
    private OnLoadCodeListener mListener;
    private OnLoadShortNameListener mShortNameListener;

    public GaodeCodeHelper() {
        mHandler = new CodeHandler(this);
    }

    private static class CodeHandler extends BaseHandler<GaodeCodeHelper> {

        public CodeHandler(GaodeCodeHelper target) {
            super(target);
        }

        @Override
        public void handle(GaodeCodeHelper target, Message msg) {
            if (msg.what == MESSAGE_GET_CODE) {
                if (target.mListener != null) {
                    target.mListener.onCodeLoaded((String) msg.obj);
                }
            } else if (msg.what == MESSAGE_GET_SHORT_NAME) {
                if (target.mShortNameListener != null) {
                    target.mShortNameListener.onShortNameLoaded((String) msg.obj);
                }
            }
        }
    }

    /**
     * 从本地json文件，获取当前城市的adcode
     *
     * @param cityCode
     * @return
     */
    public void loadCityAdCode(final String cityCode, OnLoadCodeListener listener) {
        mListener = listener;
        if (TextUtils.isEmpty(cityCode)) {
            if (mListener != null) {
                mListener.onCodeLoaded("");
            }
            return;
        }

        ThreadPoolManager.execute(new ThreadPoolManager.PriorityTask(ThreadPoolManager.PRIORITY_HIGH) {
            @Override
            public void run() {
                loadCode(cityCode);
            }
        });
    }

    /**
     * 从本地json文件，获取当前城市的简称
     *
     * @param cityCode
     * @return
     */
    public void loadShortName(final String cityCode, OnLoadShortNameListener listener) {
        mShortNameListener = listener;
        if (TextUtils.isEmpty(cityCode)) {
            if (mShortNameListener != null) {
                mShortNameListener.onShortNameLoaded("");
            }
            return;
        }

        ThreadPoolManager.execute(new ThreadPoolManager.PriorityTask(ThreadPoolManager.PRIORITY_HIGH) {
            @Override
            public void run() {
                loadCityShortName(cityCode);
            }
        });
    }

    private void loadCode(String cityCode) {
        InputStream is = null;
        try {
            StringBuffer sb = new StringBuffer();
            is = AppConfigLib.getContext().getAssets().open(FILE_AMP_JSON);
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            JSONArray province = new JSONArray(sb.toString());
            if (province == null) {
                sendMessage(MESSAGE_GET_CODE, "");
                return;
            }
            int size = province.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonProvince = province.getJSONObject(i);
                if (jsonProvince == null) {
                    continue;
                }
                JSONArray city = jsonProvince.getJSONArray("districts");
                if (city == null) {
                    continue;
                }
                int cityLength = city.length();
                for (int j = 0; j < cityLength; j++) {
                    JSONObject cityInfo = city.getJSONObject(j);
                    if (cityInfo == null) {
                        continue;
                    }
                    if (cityCode.equals(cityInfo.getString("cityCode"))) {
                        sendMessage(MESSAGE_GET_CODE, cityInfo.getString("adCode"));
                        return;
                    }
                }
            }
        } catch (IOException e) {
        } catch (JSONException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        sendMessage(MESSAGE_GET_CODE, "");
    }

    private void loadCityShortName(String adCode) {
        try {
            String cityInfoStr = readTextFile(CITY_FILE_NAME);
            JSONArray jsonArray = new JSONArray(cityInfoStr);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                if (adCode.equals(jsonObject.optString("adCode"))) {
                    sendMessage(MESSAGE_GET_SHORT_NAME, jsonObject.optString("shortName"));
                    return;
                }
            }
        } catch (Exception e) {
            LogUtils.e("===msg_local_Error", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
        sendMessage(MESSAGE_GET_SHORT_NAME, "");
    }


    public String readTextFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = AppConfigLib.getContext().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(filePath)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("readTextFile： " + e.toString());
        }
        return stringBuilder.toString();
    }

    private void sendMessage(int type, String code) {
        if (mHandler == null) {
            return;
        }
        Message message = mHandler.obtainMessage();
        message.what = type;
        message.obj = code;
        mHandler.sendMessage(message);
    }

    public interface OnLoadCodeListener {
        void onCodeLoaded(String adCode);
    }

    public interface OnLoadShortNameListener {
        void onShortNameLoaded(String adCode);
    }
}
