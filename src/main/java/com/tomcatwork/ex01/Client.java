package com.tomcatwork.ex01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ClassName Client
 * @Description 创建一个套接字，用于与本地HTTP服务进行通信（127.0.0.1表示本地主机），接收服务器的响应信息
 * @Author zzk
 * @Date 2020/11/14 23:50
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket( InetAddress.getByName("127.0.0.1"), 8080);
        OutputStream os = socket.getOutputStream();
        boolean autoflush = true;
        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), autoflush);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out.println("GET /index.html HTTP.1.1");
        out.println("Host: localhost:8080");
        out.println("Connection:Close");
        out.println();

        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while(loop){
            if(in.ready()){
                int i = 0;
                while(i != -1){
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }

        System.out.println(sb.toString());
        socket.close();
    }
}
