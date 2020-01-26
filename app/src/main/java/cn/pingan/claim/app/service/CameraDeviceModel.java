package cn.pingan.claim.app.service;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import cn.kezy.libs.common.utils.JsonUtils;
import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.base.common.net.ServiceFactory;
import cn.pingan.claim.app.base.common.net.config.UrlConfig;
import cn.pingan.claim.app.base.common.net.util.CameraCallBack;
import cn.pingan.claim.app.model.request.CameraBaseRequest;
import cn.pingan.claim.app.model.request.DeviceInfoRequest;
import cn.pingan.claim.app.model.response.CameraBaseResponse;
import cn.pingan.claim.app.model.response.CameraProjectInfoResponse;
import cn.pingan.claim.app.model.response.CameraProjectListResponse;
import cn.pingan.claim.app.model.response.DeviceInfoResponse;

/**
 * @author: Kezy
 * @create_time 2020/1/25 0025  14:08
 * @copyright kezy
 * @description:
 */
public class CameraDeviceModel {

    public static CameraDeviceService service = ServiceFactory.createService(CameraDeviceService.class, UrlConfig.CameraUrl);


    public static void getDeviceInfo(ICallBack<DeviceInfoResponse> iCallBack) {
        service.getDeviceInfo(new DeviceInfoRequest("192.168.137.142", 10073))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            DeviceInfoResponse response = JsonUtils.jsonStr2JsonObj(t, DeviceInfoResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void newProject(String DevSn, String PrjNo, ICallBack<CameraBaseResponse> iCallBack) {
        service.newProject(new CameraBaseRequest(DevSn, PrjNo))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraBaseResponse response = JsonUtils.jsonStr2JsonObj(t, CameraBaseResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getProjectList(String DevSn, ICallBack<CameraProjectListResponse> iCallBack) {
        service.getProjectList(new CameraBaseRequest(DevSn))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraProjectListResponse response = JsonUtils.jsonStr2JsonObj(t, CameraProjectListResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getProjectInfo(String DevSn, String PrjNo, ICallBack<CameraProjectInfoResponse> iCallBack) {
        service.getProjectInfo(new CameraBaseRequest(DevSn, PrjNo))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraProjectInfoResponse response = JsonUtils.jsonStr2JsonObj(t, CameraProjectInfoResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }


    public static void getCameraStatus(ICallBack<CameraBaseResponse> iCallBack) {
        service.getCameraStatus(new Object())
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraBaseResponse response = JsonUtils.jsonStr2JsonObj(t, CameraBaseResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }


    public static void recordCamera(String DevSn, String PrjNo,ICallBack<CameraBaseResponse> iCallBack) {
        service.recordCamera(new CameraBaseRequest(DevSn, PrjNo))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraBaseResponse response = JsonUtils.jsonStr2JsonObj(t, CameraBaseResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void snapCamera(String DevSn, String PrjNo,ICallBack<CameraBaseResponse> iCallBack) {
        service.snapCamera(new CameraBaseRequest(DevSn, PrjNo))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraBaseResponse response = JsonUtils.jsonStr2JsonObj(t, CameraBaseResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }
    public static void liveCamera(String DevSn, String PrjNo,ICallBack<CameraBaseResponse> iCallBack) {
        service.liveCamera(new CameraBaseRequest(DevSn, PrjNo))
                .enqueue(new CameraCallBack() {
                    @Override
                    public void onSuccess(@NotNull String t) {
                        if (!TextUtils.isEmpty(t)) {
                            CameraBaseResponse response = JsonUtils.jsonStr2JsonObj(t, CameraBaseResponse.class);
                            iCallBack.onSuccess(response);
                        } else  {
                            iCallBack.onFailure("");
                        }
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

}
