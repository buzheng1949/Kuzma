package com.buzheng.spacex.chapter2.novisibility;

/**
 * Created by buzheng on 18/1/28.
 * <p>
 * chapter2-1   可见性
 * <p>
 * 程序包含2个线程：主线程以及ReaderThread线程，当主线程改变了ready以及number的值的时候，因为主线程改变的值更新到了主内存中，
 * 但是在子线程未采取同步操作，故无法看到更新的值
 * <p>
 * 内存中变量的改变因为存在不一定能看到，可能能看到一个可能看到两个，这种现象称为重排序。
 * 在没有同步的情况下，编译器、处理器可能对操作的执行顺序进行调整优化，所以读取的时候可能读到某个值但是未能读到另外的值
 * <p>
 * 所以为什么要加锁：实际上跟JVM的内存模型有关系，加锁可以保证某个线程对共享变量的修改对其他线程是可见的
 */
public class NoVisibility {

    /**
     * 程序能否停下来
     */
    private static boolean ready;

    private static int number;

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
