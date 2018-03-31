package com.jxchexie.api;

/**
 * Created by Yi on 2018/03/10.
 */
public class Api {
    /*配置baseUlhttp://192.168.43.148:8080*/
    public static final String BASE_URL="https://server-end-android.appspot.com/phone_api/";
    /*用户登录*/
    public static final String LOGIN=BASE_URL + "login";
    /*用户注册*/
    public static final String REGISTER=BASE_URL + "register";
    /*添加请假记录*/
    public static final String ADD_HOLIDAY=BASE_URL + "add_holiday";
    /*获得用户请求记录*/
    public static final String GET_HOLIDAYS=BASE_URL + "get_holidays";
    /*用户签到*/
    public static final String SIGNIN=BASE_URL + "sign_in";
    /*获取签到记录*/
    public static final String GET_SIGNINS=BASE_URL + "get_signins";


}
