package com.buzheng.spacex.chapter1.racecondition;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by buzheng on 18/1/28.
 * 线程安全：将count++这种读取修改写入非原子性操作更换为原子性操作
 */
public class SafeCounter {
    /**
     * 替换为线程安全的基本类型
     */
    public static AtomicLong count = new AtomicLong(0);

    /**
     * 原子操作
     *
     * @return
     */
    public static Long increaseCount() {
        return count.incrementAndGet();
    }

    public static long count2 = 0;

    /**
     * 原子操作  synchronized
     *
     * @return
     */
    public static synchronized long increaseCountSafe() {
        count2 = count2 + 1;
        return count2;
    }

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Thread t1 = new Thread(new SafeCounter.UnSafeRunnable());
        Thread t2 = new Thread(new SafeCounter.UnSafeRunnable());
        t1.start();
        t2.start();
    }

    private static void test2() {
        Thread t1 = new Thread(new SafeCounter.SafeRunnable2());
        Thread t2 = new Thread(new SafeCounter.SafeRunnable2());
        t1.start();
        t2.start();
    }


    /**
     * 定义一个 Runnable
     */
    static class UnSafeRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                long result = SafeCounter.increaseCount();
                System.out.println(Thread.currentThread().getName() + " running get result is :" + result);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 定义一个 Runnable
     */
    static class SafeRunnable2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                long result = SafeCounter.increaseCountSafe();
                System.out.println(Thread.currentThread().getName() + " running get result is :" + result);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
