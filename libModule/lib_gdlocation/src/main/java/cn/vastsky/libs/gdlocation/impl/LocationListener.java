package cn.vastsky.libs.gdlocation.impl;

import cn.vastsky.libs.gdlocation.model.LocationModel;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:  定位listener
 */
public interface LocationListener {

    void onLocationFinished(boolean success, LocationModel location);
}
