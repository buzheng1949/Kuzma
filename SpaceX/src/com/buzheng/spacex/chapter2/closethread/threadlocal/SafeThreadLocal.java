package com.buzheng.spacex.chapter2.closethread.threadlocal;

import java.util.Map;

/**
 * Created by buzheng on 18/1/28.
 * <p>
 * ThreadLocal是一个实现线程封闭的很规范的做法
 * ThreadLocal的实现在之前的文章中已经提及，单个线程可以有多个ThreadLocal，而ThreadLocalMap维护了这部分关系
 * 简单理解为Map<Thread,Map<ThreadLocal,ThreadLocalData>>，先获取当前线程的ThreadLocal集合，再根据由哪个ThreadLocal发起方获取对应自己的
 * 部分数据
 * <p>
 * @Link https://buzheng1949.github.io/2017/05/10/Java-ThreadLocal/
 * @see ThreadLocal
 */
public class SafeThreadLocal {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 通过threadlocal获取结果
     *
     * @param value
     * @return
     */
    public static String get(String value) {
        String result = threadLocal.get();
        return result;
    }

    /**
     * 设置
     *
     * @param value
     */
    public static void set(String value) {
        threadLocal.set(value);
    }
}
