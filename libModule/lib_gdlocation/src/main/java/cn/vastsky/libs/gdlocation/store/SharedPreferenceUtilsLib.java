package cn.vastsky.libs.gdlocation.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class SharedPreferenceUtilsLib {
    public final static String DEFAULT_PREFERENCE_NAME = "ConsumerCredit";

    private static SharedPreferences getSharedPreferenceObject(Context context){
        return context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static String getSharedPreferences(String propertyName, Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                PreferenceActivity.MODE_PRIVATE).getString(propertyName, "");
    }

    public static boolean getSharedPreferences(String propertyName, boolean defaultValue, Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                PreferenceActivity.MODE_PRIVATE).getBoolean(propertyName, defaultValue);
    }

    public static int getSharedPreferences(String propertyName, int defaultValue, Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                PreferenceActivity.MODE_PRIVATE).getInt(propertyName, defaultValue);
    }

    public static long getSharedPreferences(String propertyName, long defaultValue, Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                PreferenceActivity.MODE_PRIVATE).getLong(propertyName, defaultValue);
    }

    public static String getSharedPreferences(String propertyName, Context context, String defaultValue) {
        return getSharedPreferenceObject(context).getString(propertyName, defaultValue);
    }

    public static String getSharedPreferences(String preferenceName, String propertyName, Context context) {
        return getSharedPreferenceObject(context).getString(propertyName, "");
    }

    public static int getSharedPreferences(String propertyName, Context context, int defaultValue) {
        return getSharedPreferenceObject(context).getInt(propertyName, defaultValue);
    }

    public static void setSharedPreferences(String propertyName, boolean propertyValue, Context context) {
        context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putBoolean(propertyName, propertyValue).commit();
    }

    public static void setSharedPreferences(String propertyName, String propertyValue, Context context) {
        context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putString(propertyName, propertyValue).commit();
    }

    public static void setSharedPreferences(String propertyName, int propertyValue, Context context) {
        context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putInt(propertyName, propertyValue).commit();
    }

    public static void setSharedPreferences(String propertyName, long propertyValue, Context context) {
        context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putLong(propertyName, propertyValue).commit();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static ArrayList<String> getSharedPreferenceList(String propertyName, Context context) {
        String regularEx = "#";
        String[] str = null;
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        String values;
        values = sp.getString(propertyName, "");
        if ("".equals(values)) {
            return null;
        }
        str = values.split(regularEx);
        return new ArrayList<String>(Arrays.asList(str));
    }

    public static void setSharedPreferenceList(String propertyName, List<String> values, Context context) {
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        if (values != null && values.size() > 0) {
            for (String value : values) {
                if ("".equals(value)) {
                    str += " ";
                } else {
                    str += value;
                }
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(propertyName, str);
            et.apply();
        }
    }
}
