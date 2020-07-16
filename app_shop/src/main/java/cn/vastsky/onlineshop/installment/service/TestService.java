package cn.vastsky.onlineshop.installment.service;

import java.util.List;

import cn.vastsky.onlineshop.installment.model.bean.base.BaseResponse;
import cn.vastsky.onlineshop.installment.model.BaseElement;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface TestService {

    @POST("/lmf-web/jyq/app/config/v2/get-tabs")
        //甲乙方、伪甲方公共
    Call<BaseResponse<List<BaseElement>>> getBottomIcons(@Body Object o);

}
