package com.ys.administrator.mydemo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

//import com.example.administrator.mydemo.download_util.DownLoadManager;
import com.alibaba.fastjson.JSON;
import com.ys.administrator.mydemo.activity.LoginActivity;
import com.ys.administrator.mydemo.application.MyApplication;
import com.ys.administrator.mydemo.http.RetrofitService;
import com.ys.administrator.mydemo.model.BaseBean;
//import com.example.administrator.mydemo.model.FileDownLoadBean;
import com.ys.administrator.mydemo.model.UserInfoBean;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.MD5;
import com.ys.administrator.mydemo.util.NetWorkTool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ys.administrator.mydemo.util.SPUtil;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyModel  {

    private static Retrofit retrofit=null;
    private static Retrofit wxRetrofit = null;
    private static Retrofit rpRetrofit = null;
    /**
     * 初始化Retrofit
     */
    private static void initRetrofit(){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .baseUrl("http://apis.juhe.cn/lottery/")
                    .baseUrl(Constant.BaseUrl)
                    .build();
        }
    }

    /**
     * 初始化微信retrofit
     */
    private static void initWXRetrofit(){
        if(wxRetrofit==null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
            wxRetrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://api.weixin.qq.com/sns/")
                    .build();
        }
    }
    /**
     */
    private static void initReport(){
        if(rpRetrofit==null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
            rpRetrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://sc.ftqq.com/")
                    .build();
        }
    }

    /**
     * 获取RetrofitService
     * @return
     */
    public static RetrofitService getRetrofitService(){
        initRetrofit();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }
    public static <T> T getRetrofitService(Class<T> tClass) { return retrofit.create(tClass); }
    /**
     * 获取RetrofitService
     * @return
     */
    public static RetrofitService getReportService(){
        initReport();
        RetrofitService retrofitService = rpRetrofit.create(RetrofitService.class);
        return retrofitService;
    }
    /**
     * 实际网络请求方法
     * @param observable Observable
     * @param callback ICallBack
     */
    public static void getNetData (Context context, Observable observable, ICallBack callback){
//        Log.d("httppost", observable.);
        initRetrofit();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onSubscribe(d);
                    }

                    @Override
                    public void onNext(Response<Object> o) {
                        Log.d("httppost", o.raw().networkResponse().request().url().toString());
                        String s = JSON.toJSONString(o.body());
                        if(o.code()==503){
                            Toast.makeText(context,"登录已过期，请重新登录",Toast.LENGTH_LONG).show();
                            Constant.clearUserInfo();
                            SPUtil.clearStore();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
//                        boolean b = o.body() instanceof BaseBean;
                        if(o.isSuccessful() && o.body()!=null /*&& b*/){
                            BaseBean bb = JSON.parseObject(s,BaseBean.class);
                            // 返回码判断请求结果
                            if(bb.getCode()==200){
                                callback.onSuccess(o.body());
                            }/*else if(bb.getCode()==503){
//                                callback.onFailure("重新登录");
                                Toast.makeText(context,"登录已过期，请重新登录",Toast.LENGTH_LONG).show();
                                Constant.clearUserInfo();
                                SPUtil.clearStore();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }*/
                            else {
                                callback.onFailure(bb.getMsg());
                            }
                        }else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("httppost", e.getLocalizedMessage()+"'");
                        callback.onFailure(e.getLocalizedMessage()+"");
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });
    }

//    public static void downLoadFile(FileDownLoadBean fileBean){
//
//        initRetrofit();
//        DownloadService downloadService = retrofit.create(DownloadService.class);
//        Observable<Response<ResponseBody>> loadFile = downloadService.retrofitDownloadFile(fileBean.getUrl(),"Last-Modified","bytes=0-");
//        loadFile.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Observer<Response<ResponseBody>>() {
//                    private  Disposable d;
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        this.d = d;
//                    }
//
//                    @Override
//                    public void onNext(Response<ResponseBody> responseBody) {
////                        DownLoadManager.writeResponseBodyToDisk(fileBean,responseBody);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) { }
//
//                    @Override
//                    public void onComplete() { }
//                });
//    }

    /**
     * demo
     *
     * @param param
     * @param callback
     */
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

    public static void getWXAccessToken(Observable<String> observable,ICallBack iCallBack){
        initWXRetrofit();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(o);
                            String access_token = jsonObject.getString("access_token");
                            String openid = jsonObject.getString("openid");
                            Map<String,String> map = new HashMap<>();
                            map.put("access_token",access_token);
                            map.put("openid",openid);
                            iCallBack.onSuccess(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            iCallBack.onFailure("json转换失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(NetWorkTool.netWorkState(MyApplication.getInstance())){
                            iCallBack.onFailure("微信登录失败");
                        }else {
                            iCallBack.onFailure("无网络连接，请检查网络");
                        }
                    }

                    @Override
                    public void onComplete() {
                        iCallBack.onComplete();
                    }
                });
    }
    public static void getUserInfo(Observable<String> observable,ICallBack iCallBack){
        initWXRetrofit();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(o);
                            String nickname = jsonObject.getString("nickname");
                            String headimgurl = jsonObject.getString("headimgurl");
                            String unionid = jsonObject.getString("unionid");
                            String openid = jsonObject.getString("openid");

                            //存储用户信息
//                            SharedPreference.getInstance().setInfo(SharedPreference.FILE_USERINFO,mContext,SharedPreference.USERINFO_WEIXIN_NICKNAME,nickname);
//                            SharedPreference.getInstance().setInfo(SharedPreference.FILE_USERINFO,mContext,SharedPreference.USERINFO_WEIXIN_HEADIMGURL,headimgurl);
//                            SharedPreference.getInstance().setInfo(SharedPreference.FILE_USERINFO,mContext,SharedPreference.USERINFO_WEIXIN_UNIONID,unionid);
//                            SharedPreference.getInstance().setInfo(SharedPreference.FILE_USERINFO,mContext,SharedPreference.USERINFO_WEIXIN_OPENID,openid);




                            iCallBack.onSuccess(null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            iCallBack.onFailure("微信获取用户信息失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(NetWorkTool.netWorkState(MyApplication.getInstance())){
                            iCallBack.onFailure("微信登录失败");
                        }else {
                            iCallBack.onFailure("无网络连接，请检查网络");
                        }
                    }

                    @Override
                    public void onComplete() {
                        iCallBack.onComplete();
                    }
                });
    }
    public static void getReport(Observable<String> observable,ICallBack iCallBack){
        initReport();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        iCallBack.onSuccess(null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(NetWorkTool.netWorkState(MyApplication.getInstance())){
                            iCallBack.onFailure("微信登录失败");
                        }else {
                            iCallBack.onFailure("无网络连接，请检查网络");
                        }
                    }

                    @Override
                    public void onComplete() {
                        iCallBack.onComplete();
                    }
                });
    }

    /**
     * 参数转换为json，生生requestBody
     * @param map
     * @return
     */
    public static RequestBody getJsonRequestBody(Object map){
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(map));
        return body;
    }

    /**
     * 一登陆的
     * 获取请求头Headers
     * @param url
     * @return
     */
    public static Map<String,String> getRequestHeaderMap(String url){
        long ts = System.currentTimeMillis();
        Map<String,String> map = new HashMap<>();
        map.put("userId",SPUtil.getId()+"");
        map.put("ts", ts +"");
        try {
            String encode = URLEncoder.encode(url, "UTF-8");
            String replace = encode.replace("%2F", "/");
            replace = replace.replace("+", "%20");
            replace = replace.replace("%28", "(");
            replace = replace.replace("%29", ")");
            map.put("sign",MD5.encodeMd5(Constant.getUserId()+ replace + ts +Constant.getToken()+Constant.getLoginAt()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map;
    }
    public static String getBitmapSign(String url){
        long ts = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        builder.append("userId=");
        builder.append(SPUtil.getId()+"&");
        builder.append("ts=");
        builder.append(ts+"&");
        try {
            String encode = URLEncoder.encode(url, "UTF-8");
            String replace = encode.replace("%2F", "/");
            replace = replace.replace("+", "%20");
            replace = replace.replace("%28", "(");
            replace = replace.replace("%29", ")");
            builder.append("sign=");
            builder.append(MD5.encodeMd5(Constant.getUserId()+ replace + ts +Constant.getToken()+Constant.getLoginAt()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
