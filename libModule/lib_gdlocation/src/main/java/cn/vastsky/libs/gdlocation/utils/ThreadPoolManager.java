package cn.vastsky.libs.gdlocation.utils;

import java.lang.annotation.Retention;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.IntDef;
import cn.vastsky.lib.utils.LogUtils;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class ThreadPoolManager {
    private static final String TAG = ThreadPoolManager.class.getSimpleName();

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    private static final int CORE_SIZE = 3;
    private static final int MAX_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 60L;


    private static final PriorityBlockingQueue<Runnable> sBlockingQueue = new PriorityBlockingQueue<>(10, new ComparePriority());

    private static final ExecutorService sThreadPool = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, sBlockingQueue);

    public static void execute(PriorityTask runnable) {
        sThreadPool.execute(runnable);
    }


    public static class PriorityTask implements Runnable {

        private int priority;

        public PriorityTask() {
        }

        public PriorityTask(@TaskPriority int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            LogUtils.i(TAG, "优先级" + priority + "的任务执行");
        }


        public int getPriority() {
            return priority;
        }
    }

    private static class ComparePriority<T extends PriorityTask> implements Comparator<T> {

        @Override
        public int compare(T lhs, T rhs) {
            return rhs.getPriority() - lhs.getPriority();
        }
    }

    @Retention(SOURCE)
    @IntDef({PRIORITY_LOW, PRIORITY_NORMAL, PRIORITY_HIGH})
    private @interface TaskPriority {
    }
}
