package com.buzheng.spacex.chapter1.racecondition;

/**
 * Created by buzheng on 18/1/28.
 * 线程不安全  典型 读取->修改->写入操作
 * 执行可以看到最后的结果并非是200，说明有值是进行了改变了的
 */
public class UnsafeCounter {

    public static long count = 0;

    /**
     * 非原子操作
     *
     * @return
     */
    public static long increaseCount() {
        count = count + 1;
        return count;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new UnSafeRunnable());
        Thread t2 = new Thread(new UnSafeRunnable());
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
                long result = UnsafeCounter.increaseCount();
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
