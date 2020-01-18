package cn.kezy.libs.common.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.kezy.libs.common.utils.JsonUtils;


/**
 * Created by mac on 2017/9/8.
 * SharedPreference封装
 */
public class SharedPrefStore implements SharedPreferences {
    private SharedPreferences sp;

    public SharedPrefStore(Context context, String name) {
        sp = context.getSharedPreferences(name,
                PreferenceActivity.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public boolean putString(String key, String value) {
        return sp.edit().putString(key, value).commit();
    }

    public boolean putStringSet(String key, Set<String> values) {
        return sp.edit().putStringSet(key, values).commit();
    }

    public SharedPrefStore putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
        return this;
    }

    public SharedPrefStore putLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
        return this;
    }

    public SharedPrefStore putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
        return this;
    }

    public SharedPrefStore putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
        return this;
    }

    public SharedPrefStore remove(String key) {
        sp.edit().remove(key).commit();
        return this;
    }

    public boolean clear() {
        return sp.edit().clear().commit();
    }

    @Override
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    @Override
     
    public String getString(String key,    String defValue) {
        return sp.getString(key, defValue);
    }

    @Override
      
    public Set<String> getStringSet(String key,    Set<String> defValues) {
        return sp.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return sp.contains(key);
    }

    @Override
    public Editor edit() {
        return sp.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean putObject(String key, Object obj) {
        return putString(key, JsonUtils.obj2JsonStr(obj));
    }

    public <T> T getObject(String key, Class<T> tClass) {
        String result = getString(key, null);
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        try {
            return JsonUtils.jsonStr2JsonObj(result, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存List
     */
    public <T> boolean putList(String tag, List<T> list) {
        if (null == list || list.size() <= 0) {
            return false;
        }
        //转换成json数据，再保存
        String strJson = JsonUtils.obj2JsonStr(list);
        return putString(tag, strJson);
    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public <T> List<T> getList(String tag, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return list;
        }
        try {
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (final JsonElement elem : array) {
                T t = JsonUtils.decode(elem, cls);
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
