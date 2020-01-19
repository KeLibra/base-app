package cn.pingan.claim.app.service;

import cn.pingan.claim.app.base.common.net.bean.BaseRequest;
import cn.pingan.claim.app.base.common.net.bean.BaseResponse;
import cn.pingan.claim.app.model.bean.InitConfigResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface TestService {


    // 首页 获取 单产品列表接口
    @POST("/init/config")
    Call<BaseResponse<InitConfigResponse>> getInitConfig(@Body BaseRequest o);

}
