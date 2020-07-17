package cn.vastsky.libs.gdlocation.utils;

import android.text.TextUtils;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class CityUtils {

    public static boolean isLastCharacter(String fullString, String charctor) {
        if (TextUtils.isEmpty(fullString) || TextUtils.isEmpty(charctor)) {
            return false;
        }

        int index = fullString.lastIndexOf(charctor);

        return index == fullString.length() - 1;
    }
}
