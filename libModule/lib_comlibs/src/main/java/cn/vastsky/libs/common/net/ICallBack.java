package cn.vastsky.libs.common.net;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface ICallBack<T> {

    void onSuccess(T t);

    void onFailure(String err_msg);
}
