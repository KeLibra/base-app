package cn.vastsky.lib.paymodule.alipay;

import cn.vastsky.lib.paymodule.alipay.bean.PayResult;

/**
 * @author: kezy
 * @create_time 2019/11/18
 * @description:
 */
public interface AlipayCallBack {
    void callBack(PayResult resultSet);
}
