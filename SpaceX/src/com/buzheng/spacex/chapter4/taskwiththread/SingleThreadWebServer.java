package com.buzheng.spacex.chapter4.taskwiththread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by buzheng on 18/1/30.
 * 单线程执行任务
 */
public class SingleThreadWebServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                handleRequest(socket);
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
