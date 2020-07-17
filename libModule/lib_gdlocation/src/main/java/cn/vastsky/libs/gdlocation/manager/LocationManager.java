package cn.vastsky.libs.gdlocation.manager;

import android.content.Context;

import cn.vastsky.libs.gdlocation.config.LocationType;
import cn.vastsky.libs.gdlocation.impl.ILocationManager;
import cn.vastsky.libs.gdlocation.impl.LocationListener;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class LocationManager implements ILocationManager{
    public static LocationManager instance = new LocationManager();

    public static LocationManager getInstance() {
        return instance;
    }

    private LocationManager() {
    }

    private ILocationManager manager;

    public void init(ILocationManager locationManager) {
        if (manager != null) {
            manager.unregister();
            manager.stop();
        }
        manager = locationManager;
        //后续可以增加别的定位
    }

    @Override
    public void register(LocationListener listener) {
        if (manager == null) {
            return;
        }
        manager.register(listener);
    }

    @Override
    public void register(LocationListener listener, boolean autoUnregisterAfterLocation) {
        if (manager == null) {
            return;
        }
        manager.register(listener, autoUnregisterAfterLocation);
    }

    @Override
    public void unregister() {
        if (manager == null) {
            return;
        }
        manager.unregister();
    }

    @Override
    public void locate(LocationType type, Context context) {
        if (manager == null) {
            return;
        }
        manager.locate(type, context);
    }

    @Override
    public void stop() {
        if (manager == null) {
            return;
        }
        manager.stop();
    }

}
