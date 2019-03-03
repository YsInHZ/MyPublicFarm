package com.ys.administrator.mydemo.util;

/**
 * Created by Administrator on 2019/1/8.
 */

public class Constant {
    public static final String APP_ID =  "wx56a88eb247ff9805";
    public static final String APP_SECRET =  "6fcb78faf3a7fdaf5a17581d965ba0b6";
    public static  boolean WX_LGOIN =  false;
//    public static  final String BaseUrl  =  "http://39.98.48.26/";
    public static  final String BaseUrl  =  "https://www.hzmyxf119.com/";
//    public static  final String BitmapBaseUrl  =  "http://39.98.48.26";
    public static  final String BitmapBaseUrl  =  "https://www.hzmyxf119.com";

    private static int userid = -1;
    private static long loginAt = -1;
    private static String token = null;
    private static String mobile = null;
    public static int getUserId(){
        if(userid==-1){
            userid = SPUtil.getId();
        }
        return userid;
    }
    public static long getLoginAt(){
        if(loginAt==-1){
            loginAt = SPUtil.getLongAt();
        }
        return loginAt;
    }
    public static String getToken(){
        if(token==null){
            token = SPUtil.getToken();
        }
        return token;
    }    public static String getMobile(){
        if(mobile==null){
            mobile = SPUtil.getMobile();
        }
        return mobile;
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo(){
        userid = -1;
        loginAt = -1;
        token = null;
         mobile = null;
    }
}
