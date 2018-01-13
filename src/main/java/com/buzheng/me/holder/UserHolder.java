package com.buzheng.me.holder;

/**
 * Created by buzheng on 18/1/13.
 */
public class UserHolder {
    private static ThreadLocal<String> userHolderThreadLocal = new ThreadLocal<>();

    /**
     * 设置进来当前用户信息
     *
     * @param user
     */
    public static void set(String user) {
        userHolderThreadLocal.set(user);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static String get() {
        return userHolderThreadLocal.get();
    }
}
