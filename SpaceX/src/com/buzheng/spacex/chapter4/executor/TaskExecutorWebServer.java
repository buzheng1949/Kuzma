package com.buzheng.spacex.chapter4.executor;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * Created by buzheng on 18/1/31.
 * 运行代码可以看到下效果，通过Executor可以将不断创建的线程请求转换为线程池中可重复利用的请求。
 * 值得注意的是：例如一个线程池是8，当使用了5条线程并且5条线程都执行完的情况下，来的第六个请求依旧会先创建新的线程
 */
public class TaskExecutorWebServer {

    public static final int MAX_THREAD_COUNT = 10;

    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD_COUNT);

    public static void main(String[] args) throws Exception {

//        withNoReturn();
        withReturnAndDo();
    }

    /**
     * 需要返回值操作 使用callble+future 其实使用futuretask一样可以实现  使用Executor节省资源，降低不必要开支，增加管理
     */
    private static void withReturnAndDo() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "i am well done";
            }
        };
        Future<String> future = executor.submit(callable);
        try {
            String s = future.get();
            //记得关掉
            executor.shutdown();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不需要返回值 直接使用runnable的方式
     */
    private static void withNoReturn() {
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("do the thing");
                    } catch (Exception e) {
                        System.out.println("do the thing error");
                        e.printStackTrace();
                    }
                }
            };
            executor.execute(runnable);
//            executor.submit(runnable);
        }
    }


    private void shutdown() {
        executor.shutdown();
    }

}
