package com.tomcatwork.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-06-30
 */
public class Response {

    private static final int BUFFER_SIZE = 1024;

    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 返回对应路径下的文件数据
     * @throws IOException
     */
    public void sendStaticResource() throws IOException{
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try{
            System.out.println(HttpServer.WEB_ROOT);
            System.out.println(request.getUri());
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if(file.exists()){
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while(ch != -1){
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            }else{
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }finally {
            if(fis != null){
                fis.close();
            }
        }
    }
}
