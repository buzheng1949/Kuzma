package com.buzheng.spacex.chapter3.syncutils.closelock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by buzheng on 18/1/30.
 * 闭锁
 */
public class CountDownLatchLock {

    private static CountDownLatch countDownLatch = new CountDownLatch(4);

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());
        Thread thread3 = new Thread(new MyRunnable());
        Thread thread4 = new Thread(new MyRunnable());
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.out.println("count down latch left :" + countDownLatch.getCount() + " and all task is done");


    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Integer time = new Random().nextInt(10) * 1000;
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "  sleep  " + time + "ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("count down latch left :" + countDownLatch.getCount());
                countDownLatch.countDown();
            }
        }
    }
}
