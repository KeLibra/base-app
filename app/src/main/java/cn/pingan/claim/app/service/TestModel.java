package cn.pingan.claim.app.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.base.common.net.ServiceFactory;
import cn.pingan.claim.app.base.common.net.bean.BaseRequest;
import cn.pingan.claim.app.base.common.net.config.UrlConfig;
import cn.pingan.claim.app.base.common.net.util.BaseCallBack;
import cn.pingan.claim.app.model.bean.InitConfigResponse;


/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public class TestModel {

    public static TestService service = ServiceFactory.createService(TestService.class);
    public static TestService service2 = ServiceFactory.createService(TestService.class, UrlConfig.CameraUrl);

    public static void getInitConfig(ICallBack<InitConfigResponse> iCallBack) {
        service.getInitConfig(new BaseRequest(new Object()))
                .enqueue(new BaseCallBack<InitConfigResponse>() {
                    @Override
                    public void onSuccess(@Nullable InitConfigResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getInitConfig2(ICallBack<InitConfigResponse> iCallBack) {
        service2.getInitConfig(new BaseRequest(new Object()))
                .enqueue(new BaseCallBack<InitConfigResponse>() {
                    @Override
                    public void onSuccess(@Nullable InitConfigResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }
}
