package com.ys.administrator.mydemo.http;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;

import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.FilePathUtil;

import okhttp3.ResponseBody;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * 思路：
 * 1Retrofit下载地址连接读取成功后，
 * 2开启子线程保存资源文件到本地
 * 3接口监听下载的进度，通知UI主线程更新下载的状态
 * Created by agen on 2018/8/24.
 */

public class DownLoadUtils {
    private static final String TAG = "DownLoadUtils";
    private static final int TIMEOUT = 10;
    private String mVideoPath; //下载到本地的视频路径
    private File mFile;
    private Thread mThread;//子线程进行io读写操作
    private Context context;
    private OkHttpClient mHttpClient;
    private Retrofit mRetrofit;

    //volatile 关键字具有屏蔽指令重排的功能，即对 instance 加上了一把锁，
    // 在完成写操作之前不会允许其他线程进行读操作，因此，在初始化完成前，无法对其进行读操作
    private static volatile DownLoadUtils instance;

    //私有化构造函数
    private DownLoadUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        mHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();
    }

    //提供对外的获取单例对象的方法
    public static DownLoadUtils getInstance() {
        //只有当 instance 为 null 的时候才执行同步代码块，二次判空保证了不会产生多个实例
        if (null == instance) {

         instance = new DownLoadUtils();


        }
        return instance;
    }

    //清理单例对象
    public static void clearInstance() {
        //只有当 instance 为 null 的时候才执行同步代码块，二次判空保证了不会产生多个实例
        if (null != instance) {

                    instance = null;

        }
    }

    public void downloadFile(String url,DownloadListener downloadListener){
        downloadFile(url,mRetrofit,downloadListener);
    }

    public void downloadFile(String url, Retrofit retrofit, final DownloadListener downloadListener) {
        Log.d(TAG,"downloadFile");
        //建立一个文件夹

        //通过Url得到保存到本地的文件名
        String name = url;
        int index = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
        String substring = url.substring(0, index);
        mFile = new File(FilePathUtil.getFilePathWithOutEnd()+substring);
        if (!mFile.exists() || !mFile.isDirectory()) {
            mFile.mkdirs();
        }
        mVideoPath = FilePathUtil.getFilePathWithOutEnd()+url;

        Log.d(TAG,"FilePath=" + mVideoPath);

        mFile = new File(mVideoPath);
        DownloadService apiStores = retrofit.create(DownloadService.class);
        Observable<Response<ResponseBody>> observable = apiStores.retrofitDownloadFile(MyModel.getRequestHeaderMap(url),url,"Last-Modified","bytes=0-");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final Response<ResponseBody> responseBody) {
                        mThread = new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                writeFileSDcard(responseBody.body(), mFile, downloadListener);
                            }
                        };
                        mThread.start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete");
                    }
                });
    }

    public void writeFileSDcard(ResponseBody responseBody, File mFile, DownloadListener downloadListener) {
        downloadListener.onStart();
        Log.d(TAG,"writeFileSDcard");
        long currentLength = 0;
        OutputStream os = null;
        if(responseBody == null){
            return;
        }
        InputStream is = responseBody.byteStream();
        long totalLength = responseBody.contentLength();
        Log.d(TAG,"totalLength=" + totalLength);
        try {
            os = new FileOutputStream(mFile);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.d(TAG,"当前长度: " + currentLength);
                int progress = (int) (100 * currentLength / totalLength);
                Log.d(TAG,"当前进度: " + progress);
                downloadListener.onProgress(progress);
                if (progress == 100) {
                    downloadListener.onFinish(mVideoPath);
                }
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG,"Exception=" + e.getMessage());
            downloadListener.onFailure("未找到文件！");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG,"Exception=" + e.getMessage());
            downloadListener.onFailure("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public interface DownloadListener {
        void onStart();

        void onProgress(int currentLength);

        void onFinish(String localPath);

        void onFailure(String msg);

    }

}
