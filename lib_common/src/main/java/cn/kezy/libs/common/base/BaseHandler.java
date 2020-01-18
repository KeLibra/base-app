package cn.kezy.libs.common.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 *   公用Handler，防止内存泄露，声明静态内部类继承此类
 */
public abstract class BaseHandler<T> extends Handler {

    private WeakReference<T> mTargets;

    public BaseHandler(T target) {
        mTargets = new WeakReference<>(target);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T target = mTargets.get();
        if (target != null) {
            handle(target, msg);
        }
    }

    public abstract void handle(T target, Message msg);
}
