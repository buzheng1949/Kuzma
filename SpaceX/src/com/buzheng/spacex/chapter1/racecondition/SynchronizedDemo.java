package com.buzheng.spacex.chapter1.racecondition;

/**
 * Created by buzheng on 18/1/28.
 * 可重入锁（自己再次获取自己所持有的锁）
 * 可重入锁的实现方法：每个锁会关联一个获取计数器跟一个持有者线程，当计数器为0的时候，标明锁当前没有被占有。
 * 当线程请求一个未被持有的锁的时候，JVM会进行记录锁的持有者并且将计数器加1，如果持有者线程再次获取锁的时候
 * 计数器将会进行递增，而当线程执行完同步代码块代码的时候，计数器进行递减，当计数器为0的时候，锁被释放
 */
public class SynchronizedDemo {
    /**
     * 父类
     */
    class Father {
        public synchronized void doSomething() {
            //方法使用synchronized 修饰 synchronized是一个内置锁  也是可重入锁
        }
    }

    /**
     * 继承自父类
     */
    class Son extends Father {

        @Override
        public synchronized void doSomething() {
            //先获取锁  再次获取锁  如果不是可重入锁（自己再次获取自己所持有的锁称之为重入）那么会产生卡住死锁的现象
            super.doSomething();
        }
    }

}
