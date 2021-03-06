package com.jerry.musicplayer.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

import android.text.TextUtils;
import android.util.Log;

import com.jerry.musicplayer.BuildConfig;

/**
 * 日志输出控制类
 *
 * @author Jerry
 * @version V1.0
 * @typename LogUtils
 * @time 2015年1月27日 上午10:23:02
 */
public class LogUtils {
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_NONE = 0;
    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 5;

    /**
     * 日志输出时的TAG
     */
    private static String mTag = "Jerry";
    /**
     * 是否允许输出log
     */
    private static int mDebuggable = LEVEL_ERROR;
    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(mTag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(String tag, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG && null != msg) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(String tag, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG && null != msg) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * 把Log存储到文件中
     *
     * @param log  需要存储的日志
     * @param path 存储路径
     */
    public static void log2File(String log, String path) {
        log2File(log, path, true);
    }

    public static void log2File(String log, String path, boolean append) {
        synchronized (mLogLock) {
            writeFile(log + "\r\n", path, append);
        }
    }

    /**
     * 把字符串数据写入文件
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(String content, String path, boolean append) {
        return writeFile(content.getBytes(), path, append);
    }

    /**
     * 把字符串数据写入文件
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(byte[] content, String path, boolean append) {
        boolean res = false;
        File f = new File(path);
        RandomAccessFile raf = null;
        try {
            if (f.exists()) {
                if (!append) {
                    f.delete();
                    f.createNewFile();
                }
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(raf.length());
                raf.write(content);
                res = true;
            }
        } catch (Exception e) {
            LogUtils.e(mTag, e);
        } finally {
            IOUtils.close(raf);
        }
        return res;
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String tag, String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e(tag, "[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public static void elapsed(String tag, String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e(tag, "[Elapsed：" + elapsedTime + "]" + msg);
    }

    public static <T> void printList(String tag, List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        i(tag, "---begin---");
        for (int i = 0; i < size; i++) {
            i(tag, i + ":" + list.get(i).toString());
        }
        i(tag, "---end---");
    }

    public static <T> void printArray(String tag, T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        i(tag, "---begin---");
        for (int i = 0; i < length; i++) {
            i(tag, i + ":" + array[i].toString());
        }
        i(tag, "---end---");
    }
}
