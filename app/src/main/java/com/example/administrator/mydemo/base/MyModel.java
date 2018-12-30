package com.example.administrator.mydemo.base;

import android.os.Environment;
import android.os.Handler;

import com.example.administrator.mydemo.download_util.DownLoadManager;
import com.example.administrator.mydemo.http.DownloadService;
import com.example.administrator.mydemo.http.RetrofitService;
import com.example.administrator.mydemo.model.BaseBean;
import com.example.administrator.mydemo.model.CaipiaoBean;
import com.example.administrator.mydemo.model.FileDownLoadBean;
import com.example.administrator.mydemo.util.FilePathUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyModel  {

    private static Retrofit retrofit=null;

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
                    .baseUrl("http://apis.juhe.cn/lottery/")
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
     * 实际网络请求方法
     * @param caipiao Observable
     * @param callback ICallBack
     */
    public static void getNetData(Observable caipiao,ICallBack callback){
        initRetrofit();
        caipiao.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseBean> o) {
                        if(o.isSuccessful() ){
                            //TODO 返回码判断请求结果
                            callback.onSuccess(o.body());
                        }else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getLocalizedMessage()+"");
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });
    }

    public static void downLoadFile(FileDownLoadBean fileBean){

        initRetrofit();
        DownloadService downloadService = retrofit.create(DownloadService.class);
        Observable<Response<ResponseBody>> loadFile = downloadService.retrofitDownloadFile(fileBean.getUrl(),"Last-Modified","bytes=0-");
        loadFile.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    private  Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBody) {
                        DownLoadManager.writeResponseBodyToDisk(fileBean,responseBody);
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { }
                });
    }

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
}
