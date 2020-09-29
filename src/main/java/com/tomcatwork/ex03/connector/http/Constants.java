package com.tomcatwork.ex03.connector.http;

import java.io.File;

/**
 * @program: testProject
 * @description: 静态常量定义
 * @author: zzk
 * @create: 2020-07-01
 */
public class Constants {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public static final String Package = "ex03.connector.http";

    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;

    public static final int PROCESSOR_IDLE = 0;

    public static final int PROCESSOR_ACTIVE = 1;
}
