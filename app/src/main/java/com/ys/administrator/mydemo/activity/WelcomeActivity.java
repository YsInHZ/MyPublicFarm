package com.ys.administrator.mydemo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.UserInfoBean;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.SPUtil;
import com.ys.administrator.mydemo.util.StatusbarUtils;

public class WelcomeActivity extends BaseActivity {

    public static final int LOGIN = 1;
    public static final int NOTLOGIN = 2;
    public static final int FIRSTOPEN = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_welcome);
        if(false){//是否第一次登录
            handler.sendEmptyMessageDelayed(FIRSTOPEN,3000);
        }else if(!TextUtils.isEmpty(Constant.getToken()) && SPUtil.getExpireAt()>System.currentTimeMillis()){//已经登录
            handler.sendEmptyMessageDelayed(LOGIN,3000);
        }else {//未登录
            handler.sendEmptyMessageDelayed(NOTLOGIN,3000);
        }
//        if(TextUtils.isEmpty(Constant.getToken())){
//            if(true){//判断是否第一次登录 跳转引导页/登录页
//                handler.sendEmptyMessageDelayed(NOTLOGIN,3000);
//            }else {
//
//            }
//        }else {
//            getToken();
//        }

    }

    private void getToken(){
        MyModel.getNetData(MyModel.getRetrofitService().getUserToken(MyModel.getRequestHeaderMap("/user/token")), new ICallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                SPUtil.saveUserToken(data.getUser());
                handler.sendEmptyMessageDelayed(LOGIN,3000);
            }

            @Override
            public void onFailure(String msg) {
                handler.sendEmptyMessageDelayed(NOTLOGIN,3000);
            }

            @Override
            public void onError() {
                handler.sendEmptyMessageDelayed(NOTLOGIN,3000);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    @Override
    public void showData(Object data) {

    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == LOGIN){
                openActivity(IndexActivity.class);
            }else if(msg.what == NOTLOGIN){
                openActivity(LoginActivity.class);
            }
        }
    };
}
