package cn.pingan.claim.app.base.common.net.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kezy.libs.common.utils.LogUtils;


public class SecurityUtils {

    private static final String CIPHER_KEY = "U2FsdGVkX186ABhUO0sC3hhUxa4cpt5r";

    public static String genSignMsg(Map<String, String> sParaTemp, String key) {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String sign = "";

        try {
            sign = buildRequestByMD5(sPara, key);
        } catch (Exception e) {
            LogUtils.e("SecurityUtils_error", e.toString());
        }
        return sign;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (TextUtils.isEmpty(value) || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type") || value.equals("null")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key) throws Exception {
        String preStr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mySign = "";
        mySign = MD5.sign(preStr, key);
        return mySign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder preStr = new StringBuilder(64);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i != 0) {
                preStr.append("&");
            }
//            preStr.append(preStr).append(key).append("=").append(value);
            preStr.append(key).append("=").append(value);
        }
        return preStr.toString();
    }

    public static String doubleSHA(String pass) {
        try {
            pass = SecurityUtils.getSHA(pass);
            pass = SecurityUtils.getSHA(pass + CIPHER_KEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pass;
    }

    private static String getSHA(String val) throws NoSuchAlgorithmException {
        MessageDigest sda = MessageDigest.getInstance("SHA-256");
        sda.update(val.getBytes());
        byte[] m = sda.digest();//加密
        return SecurityUtils.byte2hex(m);
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
}
