package com.tomcatwork.ex03.startup;

import com.tomcatwork.ex03.connector.http.HttpConnector;

/**
 * @program: testProject
 * @description: 启动类
 * @author: zzk
 * @create: 2020-08-03
 */
public class Bootstrap {

    public static void main(String[] args){
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
