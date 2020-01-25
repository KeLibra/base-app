package cn.pingan.claim.app.service;

import org.jetbrains.annotations.NotNull;

import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.base.common.net.ServiceFactory;
import cn.pingan.claim.app.base.common.net.config.UrlConfig;
import cn.pingan.claim.app.base.common.net.util.CameraCallBack;
import cn.pingan.claim.app.model.request.DeviceInfoRequest;

/**
 * @author: Kezy
 * @create_time 2020/1/25 0025  14:08
 * @copyright kezy
 * @description:
 */
public class CameraDeviceModel {

    public static CameraDeviceService service = ServiceFactory.createService(CameraDeviceService.class, UrlConfig.CameraUrl);

    public static void getDeviceInfo(ICallBack<String> iCallBack) {
        service.getDeviceInfo(new DeviceInfoRequest("192.168.137.142", 8080))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        iCallBack.onSuccess(t);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

}
