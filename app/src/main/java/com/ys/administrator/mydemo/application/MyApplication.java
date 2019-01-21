package com.ys.administrator.mydemo.application;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application{
    private static  Context context;
    protected static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
//    private static DaoSession daoSession;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = this;
//        initGreenDao();
//
//    }
//    private void initGreenDao(){
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"FileInfo.db");
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//    }
//    public static  Context getContext() {
//        return context;
//    }
//    public static DaoSession getDaoSession(){
//        return  daoSession;
//    }
public static MyApplication getInstance()
{
    return mInstance;
}
}
