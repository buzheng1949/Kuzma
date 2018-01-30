package com.buzheng.spacex.chapter3.syncutils.closelock;

import java.util.concurrent.Semaphore;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by buzheng on 18/1/30.
 */
public class SemaphoreLock {

    static Semaphore semaphore = new Semaphore(3);

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                //从信号量中获取一个机会 如果没有则阻塞等待操作 如果获取得到就执行
                System.out.println("left change:"+ semaphore.availablePermits());
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " start at " + System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " stop at " + System.currentTimeMillis());
                //释放机会
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }
}
