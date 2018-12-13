package com.example.administrator.mydemo.base;

import android.os.Handler;
/**
 * Created by Administrator on 2018/8/2.
 */

public class MyModel  {

    public static void getNetData(final String param, final ICallBack<String> callback){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (param){
                    case "normal":
                        callback.onSuccess("根据参数"+param+"的请求网络数据成功");
                        break;
                    case "failure":
                        callback.onFailure("请求失败：参数有误");
                        break;
                    case "error":
                        callback.onError();
                        break;
                }
                callback.onComplete();
            }
        },2000);
    }
}
