package cn.vastsky.lib.utils;


import org.greenrobot.eventbus.EventBus;

/**
 * @ClassName EventBusUtils
 * @Description: 用来管理EventBus
 */
public class EventBusUtils {
    /**
     * 注册EventBus
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (subscriber == null) return;
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 解注册EventBus
     *
     * @param subscriber
     */
    public static void unRegister(Object subscriber) {
        if (subscriber == null) return;
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void sendBaseTestEvent(Object event) {
        EventBus.getDefault().post(event);
    }
}
