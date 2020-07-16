package cn.vastsky.onlineshop.installment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.vastsky.onlineshop.installment.common.net.BaseCallBack;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.net.ServiceFactory;
import cn.vastsky.onlineshop.installment.model.BaseElement;


/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public class TestModel {

    public static TestService testService = ServiceFactory.createService(TestService.class);

    public static void getBottomIcon(final ICallBack<List<BaseElement>> iCallBack) {
        testService.getBottomIcons(new Object())
                .enqueue(new BaseCallBack<List<BaseElement>>() {
                    @Override
                    public void onSuccess(@Nullable List<BaseElement> baseElements) {
                        iCallBack.onSuccess(baseElements);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }
}
