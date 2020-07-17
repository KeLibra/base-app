package cn.vastsky.onlineshop.installment.model.bean.event;

import cn.vastsky.lib.base.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/25
 * @description:
 */
public class MessageEvent extends BaseBean {

    public final static String TYPE_LOGIN_SUCC = "login_succ";
    public final static String TYPE_LOGIN_CALLBACK = "login_Callback";
    public final static String TYPE_FINISH_PAYMENT = "finish_payment";

    public String eventType;

    public Object message;

    public MessageEvent(String eventType) {
        this.eventType = eventType;
    }


    public MessageEvent(String eventType, Object message) {
        this.eventType = eventType;
        this.message = message;
    }
}
