package com.buzheng.spacex.chapter3.syncutils.closelock;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by buzheng on 18/1/30.
 * Callable是一种可生成结果的Runnable
 * FutureTask是一个阻塞操作，当task在线程执行完的时候，get立刻获取，如果未执行完，会处于等待的状态
 */
public class FutureTaskLock {
    private static final FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task1 is finished");
            return "task1 is finished";
        }
    });

    private static final FutureTask<String> futureTask2 = new FutureTask<String>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task2 is finished");
            return "task2 is finished";
        }
    });

    public static void main(String[] args) {
        Thread thread = new Thread(futureTask);
        Thread thread1 = new Thread(futureTask2);
        thread.start();
        thread1.start();
        System.out.println("start time is " + System.currentTimeMillis() + "ms");
        try {
            futureTask.get();
            futureTask2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("task is over,end time is " + System.currentTimeMillis() + "ms");
    }
}
