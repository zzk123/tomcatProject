package com.tomcatwork.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: testProject
 * @description: 负责创建一个服务器套接字，该套接字会等待传入HTTP请求
 * @author: zzk
 * @create: 2020-07-01
 */
public class HttpConnector implements Runnable {

    boolean stopped;

    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try{
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        while(!stopped){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            }catch (Exception e){
                continue;
            }
            //为每个请求创建一个HttpProcessor实例，调用process方法
            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);
        }
    }

    /**
     * 启动应用程序
     */
    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
}
