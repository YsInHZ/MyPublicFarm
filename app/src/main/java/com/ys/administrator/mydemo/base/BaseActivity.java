package com.ys.administrator.mydemo.base;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.CustomDensity;
import com.ys.administrator.mydemo.view.CommonView;

import java.util.Stack;

/**
 * Created by Administrator on 2018/8/2.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView , CommonView {
    /** 加载中Dialog */
    private ProgressDialog mProgressDialog;
    /** 用来保存当前Activity的Context */
    protected Context mContext;
    /** 用来保存所有已打开的Activity */
    private static Stack<Activity> listActivity = new Stack<Activity>();
    /** 记录上次点击按钮的时间 **/
    private long lastClickTime;
    /** 按钮连续点击最低间隔时间 单位：毫秒 **/
    public final static int CLICK_TIME = 500;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    /***拦截返回按键**/
    protected boolean interceptKeyBack = false;
    /**标题栏*/
    private TextView title;
    protected CommonPresenter commonPresenter;
    protected MyFillDialog upingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityState(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请求数据中");
        mProgressDialog.setCancelable(false);
        mContext = this;
        listActivity.push(this);
        CustomDensity.setCustomDensity(this,getApplication());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除当前activity
        commonPresenter.dettachView();
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }

    }
    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void showToast(String msg) {
        if(msg!=null && !msg.isEmpty()){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void showErr() {
//        showToast(getResources().getString(R.string.api_error_msg));
        showToast("");
    }
    @Override
    public Context getContext() {
        return mContext;
    }
    /** 收起键盘 */
    public void closeInputMethod() {
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**关闭所有(前台、后台)Activity,注意：请已BaseActivity为父类*/
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
    }
    /**关闭所有(前台、后台) 除了当前传入的class*/
    protected static void finishAllWithOut(Class cl) {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {

            Activity activity = listActivity.pop();
            if(!activity.getClass().equals(cl)){
                activity.finish();
            }

        }
    }
    /** 验证上次点击按钮时间间隔，防止重复点击 重复点击返回false*/
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }
    /**
     * 设置屏幕只能竖屏
     * @param activity
     * activity
     */
    public void setActivityState(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /**打开Activity各种方式*/
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }
    /**
     * 初始化logding
     */
    private void initUpingDialog(){
        upingDialog =  new MyFillDialog(mContext,R.layout.dialog_loaging);
        upingDialog.setCancelable(false);
    }
    ObjectAnimator rotation = null;

    /**
     * 显示上传中 loading
     */
    protected void showUpingDialog(){
        if(upingDialog==null){
            initUpingDialog();
        }
        ImageView viewById = (ImageView) upingDialog.findViewById(R.id.img);
        rotation = ObjectAnimator.ofFloat(viewById, "rotation", 0f, 360f);
        rotation.setRepeatCount(-1);
        rotation.setDuration(1000);
        rotation.start();
        upingDialog.show();

    }
    protected void hideBottomNavigation(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
    }
    /**
     * 关闭上传中 loading
     */
    protected void closeUpingDialog(){
        if(rotation!=null && rotation.isRunning()){
            rotation.cancel();
        }
        if(upingDialog==null){
            return;
        }
        upingDialog.dismiss();

    }
    public void initToolbar(String titleString){
        initToolbar(titleString,true);
    }
    public void initToolbar(String titleString,boolean back) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title = findViewById(R.id.toolbar_title);
        if(back){
            toolbar.setNavigationIcon(R.mipmap.img_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeInputMethod();
                    ((Activity)mContext).finish();

                }
            });
        }

        title.setText(titleString);
    }
    /***************** 双击退出程序 ************************************************/
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode && interceptKeyBack) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
