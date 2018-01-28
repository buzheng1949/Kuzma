package com.buzheng.spacex.chapter2.closethread.unchange;

/**
 * Created by buzheng on 18/1/28.
 * 如果对象是不可变的，同样满足线程安全：
 * 1、对象创建之后状态就不能被修改
 * 2、对象是final域之内
 * 3、对象是正确创建的
 */
public class UnChange {
    //1、声明为final 表明不可被修改 就算是发布到不安全的代码环境中，依旧无法被修改，通过Java保证
    public static final int unchangebleNum = 1;
}
