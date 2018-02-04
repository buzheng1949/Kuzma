package com.buzheng.spacex.chapter4.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by buzheng on 18/2/4.
 * 多线程并发访问（区别在于这个是一个blockqueue模型 效率高）
 */
public class CompletionServiceWeb {

    static List<Task> taskList = new ArrayList<>();

    public static void init() {
        taskList.add(new Task2());
        taskList.add(new Task2());
        taskList.add(new Task1());
        taskList.add(new Task1());
    }

    public static void testCompletionService(ExecutorService executor) {
        CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
        for (final Task task : taskList) {
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
//                    System.out.println(task.testTask());
                    return task.testTask();
                }
            });
        }
        for (int i = 0; i < taskList.size(); i++) {
            try {
                Future<String> future = completionService.take();
                String s = future.get();
                System.out.println(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    interface Task {
        String testTask();
    }

    static class Task1 implements Task {
        @Override
        public String testTask() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread:" + Thread.currentThread().getName() + " task1 is start");
            return "Thread:" + Thread.currentThread().getName() + " task1 is end";
        }
    }

    static class Task2 implements Task {
        @Override
        public String testTask() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread:" + Thread.currentThread().getName() + " task2 is start");
            return "Thread:" + Thread.currentThread().getName() + " task2 is end";
        }
    }

    public static void main(String[] args) {
        init();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        testCompletionService(executorService);
    }
}
