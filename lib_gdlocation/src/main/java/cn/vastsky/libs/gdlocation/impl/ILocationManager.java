package cn.vastsky.libs.gdlocation.impl;

import android.content.Context;

import cn.vastsky.libs.gdlocation.config.LocationType;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public interface ILocationManager {

    void register(LocationListener listener);

    void register(LocationListener listener, boolean autoUnregisterAfterLocation);

    void unregister();

    void locate(LocationType type, Context context);

    void stop();
}
