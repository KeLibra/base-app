package cn.kezy.libs.common.utils;

import android.text.TextUtils;
import android.util.Log;

import cn.kezy.libs.common.BuildConfig;


/**
 * <p>
 * debug 调试日志
 * <p>
 * 日志类，支持普通及超长日志的打印
 */

public class LogUtils {


    private final static int SEGMENT_MAX_SIZE = 3 * 1024;
    private final static int STACK_TRACE_ELEMENT_INDEX = 4;
    private final static int V = 1;
    private final static int D = 2;
    private final static int I = 3;
    private final static int W = 4;
    private final static int E = 5;
    private final static int INFO = 6;

    private final static boolean isPrintLog = BuildConfig.DEBUG; // 是否开启日志 ， 线上环境只开 info 和 e
//    private final static boolean isPrintLog = true; // 是否开启日志 ， 线上环境只开 info 和 e


    public static void e(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(E, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(E, "", logContent);
            }
            formatLog(E, "", message);
        }
    }


    /**
     * 对应Log.e(String tag, String msg)进行打印，日志等级：错误
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void e(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(E, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(E, tag, logContent);
            }
            formatLog(E, tag, message);
        }
    }


    public static void i(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(I, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(I, "", logContent);
            }
            formatLog(I, "", message);
        }
    }


    /**
     * 对应Log.i(String tag, String msg)进行打印，日志等级：debug
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void i(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(I, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(I, tag, logContent);
            }
            formatLog(I, tag, message);
        }
    }

    public static void w(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(W, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(W, "", logContent);
            }
            formatLog(W, "", message);
        }
    }


    /**
     * 对应Log.w(String tag, String msg)进行打印，日志等级：debug
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void w(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(W, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(W, tag, logContent);
            }
            formatLog(W, tag, message);
        }
    }

    public static void v(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(V, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(V, "", logContent);
            }
            formatLog(V, "", message);
        }
    }


    /**
     * 对应Log.v(String tag, String msg)进行打印，日志等级：debug
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void v(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(V, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(V, tag, logContent);
            }
            formatLog(V, tag, message);
        }
    }

    public static void d(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(D, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(D, "", logContent);
            }
            formatLog(D, "", message);
        }
    }


    /**
     * 对应Log.d(String tag, String msg)进行打印，日志等级：debug
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void d(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(D, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(D, tag, logContent);
            }
            formatLog(D, tag, message);
        }
    }

    public static void info(Object object) {
        boolean illegalObject = false;
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(INFO, "", message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(INFO, "", logContent);
            }
            formatLog(INFO, "", message);
        }
    }


    /**
     * 对应Log.d(String tag, String msg)进行打印，日志等级：debug
     *
     * @param tag    标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     */
    public static void info(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            formatLog(INFO, tag, message);
        } else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                formatLog(INFO, tag, logContent);
            }
            formatLog(INFO, tag, message);
        }
    }


    /**
     * 格式化并执行打印
     *
     * @param type   日志等级，包含 5 种：V、D、I、W、E，且只能是这4个
     * @param tag    标签
     * @param object 日志内容
     */
    private static void formatLog(int type, String tag, Object object) {

        String tagStr = "";
        String msgContentStr = "";

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String filename = stackTraceElements[STACK_TRACE_ELEMENT_INDEX].getFileName();
        String classStr = stackTraceElements[STACK_TRACE_ELEMENT_INDEX].getClassName();
        String methodStr = stackTraceElements[STACK_TRACE_ELEMENT_INDEX].getMethodName();
        int lineNumber = stackTraceElements[STACK_TRACE_ELEMENT_INDEX].getLineNumber();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[(").append(filename).append(":").append(lineNumber).append(")#").append(methodStr).append("]  ");

        String messageContent = object.toString();

        /**
         * 如果输入 tag， 则 tag 为 tag，  msg 内容为  自定义内容 + 日志
         * 否则 tag 为 自定义内容， msg 内容 为 日志
         */
        if (!TextUtils.isEmpty(tag)) {
            tagStr = tag;
            msgContentStr = stringBuilder.append(messageContent).toString();
        } else {
            tagStr = stringBuilder.toString();
            msgContentStr = messageContent;
        }


        switch (type) {
            case V:
                if (isPrintLog) {
                    Log.v(tagStr, msgContentStr);
                }
                break;
            case D:
                if (isPrintLog) {
                    Log.d(tagStr, msgContentStr);
                }
                break;
            case I:
                if (isPrintLog) {
                    Log.i(tagStr, msgContentStr);
                }
                break;
            case W:
                if (isPrintLog) {
                    Log.w(tagStr, msgContentStr);
                }
                break;
            case E:
                Log.e(tagStr, msgContentStr);
                break;
            case INFO:
                Log.d(tagStr, msgContentStr);
                break;
            default:
                break;
        }
    }
}
