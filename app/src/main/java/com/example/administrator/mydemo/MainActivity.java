package com.example.administrator.mydemo;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.mydemo.base.ICallBack;
import com.example.administrator.mydemo.base.MyModel;
import com.example.administrator.mydemo.model.CaipiaoBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

//import com.example.administrator.mydemo.db.DaoSession;
//import com.example.administrator.mydemo.db.FileDownLoadBeanDao;
//import com.example.administrator.mydemo.model.FileDownLoadBean;
//import com.example.administrator.mydemo.util.DB_Util;
//import org.greenrobot.greendao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DownLoad";

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String action = getIntent().getAction();
        if (action != null && getIntent().ACTION_VIEW.equals(action)) {
            String dataString = getIntent().getDataString();
            if (dataString.indexOf("file:///") == 0) {
                dataString = dataString.substring(7);
            }
            Log.d(TAG, "传入打开文件\n路径为： " + dataString);
            try {
                dataString = URLDecoder.decode(dataString, "utf-8");
                Log.d(TAG, "第一次转义后\n路径为： " + dataString);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String finalDataString = dataString;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ContentResolver contentResolver = getContentResolver();
                    Uri uri = new Uri.Builder().build();

                    File file = new File(finalDataString);
                    try {
                        Log.d(TAG, "开始读流");
                        InputStream inputStream = new FileInputStream(file);
                        Log.d(TAG, "打开文件流");
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                        Log.d(TAG, "打开inputStreamReader");
                        BufferedReader br = new BufferedReader(inputStreamReader);
                        Log.d(TAG, "打开BufferedReader");
                        String str = null;
                        while ((str = br.readLine()) != null) {
                            Log.d(TAG, "run: " + str);
                        }
                        br.close();
                    } catch (FileNotFoundException e) {
                        Log.d(TAG, e.getMessage() + "");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.d(TAG, e.getMessage() + "");
                        e.printStackTrace();
                    } finally {

                    }
                }
            });
            thread.start();
        } else {
            Log.d(TAG, "未传入打开文件 ");
        }
//        getNetData();
//        getDownLoad();
//        DbTest();

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String o) {
                System.out.println("I Observer a msg" + o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //1.实例化被观察者
        //2.设置被观察者发出去的数据
        Observable<String> observable = Observable.just("XXX1", "XXX2", "XXX3", "XXX4");
        observable.doOnDispose(new Action() {
            @Override
            public void run() throws Exception {

            }
        });
        //为被观察者设置观察者
        observable.subscribe(observer);
    }


//    private void DbTest(){
//        List<FileDownLoadBean> fb = MyApplication.getDaoSession().getFileDownLoadBeanDao().loadAll();
//        for (FileDownLoadBean ff:fb) {
//            Log.d(TAG, "fileUrl: "+ff.getUrl()+"\nFilePath:"+ff.getFilePath());
//        }
//
//        FileDownLoadBean file = new FileDownLoadBean();
//        file.setUrl("http://downza.91speed.com.cn/2016/10/xljsb1034360.zip");
//        file.setFilePath("kkd/");
//        file.setFileName("ddt");
//
//
//        DB_Util.upDataFileInfo(file);
//
//
//
//        List<FileDownLoadBean> fileDownLoadBeans = MyApplication.getDaoSession().getFileDownLoadBeanDao().loadAll();
//        for (FileDownLoadBean ff:fileDownLoadBeans) {
//            Log.d(TAG, "fileUrl: "+ff.getUrl()+"\nFilePath:"+ff.getFilePath());
//        }
//    }
//    private void getDownLoad() {
//        FileDownLoadBean file = new FileDownLoadBean();
//        file.setUrl("http://downza.91speed.com.cn/2016/10/xljsb1034360.zip");
//        file.setFilePath("kkd/");
//        DaoSession daoSession = MyApplication.getDaoSession();
//        FileDownLoadBeanDao fileDownLoadBeanDao = daoSession.getFileDownLoadBeanDao();
//        List<FileDownLoadBean> fileDownLoadBeans = fileDownLoadBeanDao.loadAll();
//        for (FileDownLoadBean ff:fileDownLoadBeans) {
//            Log.d(TAG, "fileUrl: "+ff.getUrl()+"\nFilePath:"+ff.getFilePath());
//        }
//        MyModel.downLoadFile(file);
//        CompositeDisposable disposables = new CompositeDisposable();
//        disposables.dispose();
//    }

    public void getNetData() {
        MyModel.getNetData(MyModel.getRetrofitService().getCaipiao("a0912c42407c782251ae0d46af098462"), new ICallBack<CaipiaoBean>() {
            @Override
            public void onSuccess(CaipiaoBean data) {
                Log.d(TAG, "caipiaoBeanResult.getReason: " + data.getReason());
                List<CaipiaoBean.ResultBean> result = data.getResult();
                for (CaipiaoBean.ResultBean s : result) {
                    Log.d(TAG, "" + s.getLottery_name());
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: " + msg);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }


    @OnClick({R.id.start, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:

                break;
            case R.id.stop:
                break;
        }
    }
}
