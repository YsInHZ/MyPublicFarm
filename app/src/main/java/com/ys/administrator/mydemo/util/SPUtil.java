package com.ys.administrator.mydemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ys.administrator.mydemo.application.MyApplication;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.model.UserInfoBean;

/**
 * 本地存储封装
 */
public class SPUtil {
    private static SharedPreferences sharedPreferences;
    private static void initSharedPreferences(String FILE_NAME){
            sharedPreferences = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
    private static SharedPreferences.Editor initEditor(String FILE_NAME){
        initSharedPreferences(FILE_NAME);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        return edit;
    }

    /**
     * 保存用户信息
     * @param userBean
     */
    public static  void saveUserInfo(UserInfoBean.UserBean userBean){
        if(userBean==null){
            return;
        }
        SharedPreferences.Editor editor = initEditor("UserInfoBean");
        editor.putString("token",userBean.getLoginToken());
        editor.putString("mobile",userBean.getMobile());
        editor.putInt("Id",userBean.getId());
        editor.commit();
    }

    public static String getToken(){
        initSharedPreferences("UserInfoBean");
        String token = sharedPreferences.getString("token", "");
        return token;
    }
    public static String getMobile(){
        initSharedPreferences("UserInfoBean");
        String token = sharedPreferences.getString("mobile", "");
        return token;
    }
    public static int getId(){
        initSharedPreferences("UserInfoBean");
        int token = sharedPreferences.getInt("Id", 0);
        return token;
    }
    /**
     * 状态列表
     * @param statusListBean
     */
    public static  void saveStatusList(StatusListBean statusListBean){
        if(statusListBean==null){
            return;
        }
        SharedPreferences.Editor editor = initEditor("StatusListBean");
        editor.putString("data",JSON.toJSONString(statusListBean));
        editor.commit();
    }
    /**
     * 获取状态列表
     * @return
     */
    public static  StatusListBean getStatusList( ){
        initSharedPreferences("StatusListBean");
        String data = sharedPreferences.getString("data", "");
        StatusListBean statusListBean = JSON.parseObject(data, StatusListBean.class);
        return statusListBean;
    }

}
