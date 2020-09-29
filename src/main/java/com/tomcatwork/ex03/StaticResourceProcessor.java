package com.tomcatwork.ex03;

import com.tomcatwork.ex03.connector.http.HttpRequest;
import com.tomcatwork.ex03.connector.http.HttpResponse;

import java.io.IOException;

/**
 * @program: testProject
 * @description: 静态资源获取
 * @author: zzk
 * @create: 2020-08-03
 */
public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response){
        try{
            response.sendStaticResource();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
