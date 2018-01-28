package com.buzheng.spacex.chapter1.racecondition;

import net.jcip.annotations.NotThreadSafe;

/**
 * Created by buzheng on 18/1/28.
 * 竞态条件：并发执行过程中，程序执行的结果的正确与否取决于运气
 * 如下面的例子：
 * 当两个线程A、B同时获取LazyInitInstance实例的时候，A线程进行观察后发现还没有实例，进行创建
 * 在创建的时候并且还未创建成功的时候B同样执行操作，发现还没创建，那么此时就不一致了，这是一种典型的先观察后操作的过程，
 * 而这种过程并不可靠，除非一直维持观察。
 */
public class LazyInitInstance {

    private LazyInitInstance() {
    }

    private static LazyInitInstance instance = null;

    /**
     * 产生竞态条件的case
     *
     * @return
     */
    public static LazyInitInstance getInstance() {
        if (instance == null) {
            instance = new LazyInitInstance();
        }
        return instance;
    }

}
