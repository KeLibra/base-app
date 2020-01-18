package cn.kezy.libs.common.store;

import android.content.Context;

import java.util.WeakHashMap;

/**
 *
 * 持久化存储封装
 */
public class Store {

    protected static final String DEFAULT_NAME = "ConsumerCredit";

    private static WeakHashMap<String, SharedPrefStore> spMap = new WeakHashMap<>();

    public static SharedPrefStore sp(Context context) {
        if (spMap.get(DEFAULT_NAME) != null) {
            return spMap.get(DEFAULT_NAME);
        }
        SharedPrefStore spStore = new SharedPrefStore(context, DEFAULT_NAME);
        spMap.put(DEFAULT_NAME, spStore);
        return spStore;
    }

    public static SharedPrefStore sp(Context context, String name) {
        if (spMap.get(name) != null) {
            return spMap.get(name);
        }
        SharedPrefStore spStore = new SharedPrefStore(context, name);
        spMap.put(name, spStore);
        return spStore;
    }


}
