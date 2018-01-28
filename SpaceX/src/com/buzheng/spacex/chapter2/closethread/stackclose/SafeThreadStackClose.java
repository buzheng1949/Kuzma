package com.buzheng.spacex.chapter2.closethread.stackclose;

import java.util.Collection;
import java.util.SortedSet;

/**
 * Created by buzheng on 18/1/28.
 * 线程封闭：在线程不安全的情况下，数据都是共享的，因此要保证共享数据的可见性，线程封闭指的是不共享数据，只在单线程之间访问，就不需要进行同步
 * <p>
 * 1、栈封闭：线程只能访问自己线程栈中的数据，并且独一份是自己的
 */
public class SafeThreadStackClose {
    private int getResult(int inputNum) {
        //变量是局部变量 不进行共享 单个线程只有一份 不会被发布
        int result = 1;
        result = result + inputNum;
        return result;
    }
}
