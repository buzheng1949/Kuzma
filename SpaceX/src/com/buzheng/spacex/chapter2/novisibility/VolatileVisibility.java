package com.buzheng.spacex.chapter2.novisibility;

/**
 * Created by buzheng on 18/1/28.
 * <p>
 * volatile:一种稍弱的加锁机制，能禁止重排序以及强制将工作线程中的数据修改后更新到主内存中，在获取前也会去主内存进行同步
 * 所以任意线程修改的数据都是可见的
 * <p>
 * volatile,synchronized:volatile不加锁，不会导致线程阻塞，是一个轻量级的同步机制，只保证可见性，原子性无法保证
 */
public class VolatileVisibility {
    /**
     * 程序能否停下来
     */
    private volatile static boolean ready;

    private volatile static int number;

    /**
     * 读线程
     */
    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        ReaderThread readerThread = new ReaderThread();
        readerThread.start();
        number = 42;
        ready = true;
    }
}
