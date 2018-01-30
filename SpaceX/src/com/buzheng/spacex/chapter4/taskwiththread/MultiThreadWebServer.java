package com.buzheng.spacex.chapter4.taskwiththread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by buzheng on 18/1/31.
 * 每一个请求都创造线程去响应 不占用主线程的资源
 */
public class MultiThreadWebServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(socket);
                    }
                };
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }

    }

    public static void handleRequest(Socket socket) {
        //do somethind
    }
}
