package com.example.springcloudgateway.config;

/**
 * @version V1.0
 * @author: hqk
 * @date: 2020/5/12 15:59
 * @Description:
 */
public class AppConstants {


    public static final Integer SUCCESS_CODE = 200;

    public static final Integer ERROR_CODE = 401;

    /**
     *  token 参数
     */
    public static final String TOKEN = "ticket";


    /**
     * redis token
     */
    public static final String REDIS_KEY_TOKEN = "haohong:token:ticket";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";


    /**
     * 参数管理 cache name
     */
    public static final String SYS_CONFIG_CACHE = "sys-config";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";


}
