package com.tomcatwork.ex02.servlet001;

import java.io.IOException;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-07-01
 */
public class StaticResourceProcessor {

    /**
     * 读取资源数据
     * @param request
     * @param response
     */
    public void process(Request request, Response response){
        try{
            response.sendSaticResource();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
