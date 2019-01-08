package com.example.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.base.BaseActivity;
import com.example.administrator.mydemo.base.ICallBack;
import com.example.administrator.mydemo.base.MyModel;
import com.example.administrator.mydemo.presenter.CommonPresenter;
import com.example.administrator.mydemo.util.Constant;
import com.example.administrator.mydemo.util.PhoneUtil;
import com.example.administrator.mydemo.util.StatusbarUtils;
import com.example.administrator.mydemo.util.WeiXinUtil;
import com.example.administrator.mydemo.view.CommonView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *登录
 */
public class LoginActivity extends BaseActivity  {
    @BindView(R.id.tx)
    TextView tx;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etSecreat)
    EditText etSecreat;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.ivWei)
    ImageView ivWei;
    @BindView(R.id.tvRergest)
    TextView tvRergest;
    @BindView(R.id.tvForget)
    TextView tvForget;


    String phoneString;
    String secretString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);

    }



    @Override
    public void showData(Object data) {

    }


    @OnClick({R.id.tvLogin, R.id.ivWei, R.id.tvRergest, R.id.tvForget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                if(verifyClickTime() && checkInput()){
                    //TODO 网络请求进行登录
                }

                break;
            case R.id.ivWei:
                //  微信登录
                if(verifyClickTime()){
                    Constant.WX_LGOIN = false;
                    WeiXinUtil.WXLogin();
                }
                break;
            case R.id.tvRergest:
                //  注册
                if(verifyClickTime()){
                    openActivity(RegisterActivity.class);
                }


                break;
            case R.id.tvForget:
                //  忘记密码
                if(verifyClickTime()){
                    openActivity(RetrieveActivity.class);
                }

                break;
        }
    }

    private boolean checkInput() {
        phoneString = etPhone.getText().toString().trim();
        secretString = etSecreat.getText().toString().trim();
        if(phoneString.length()!=11 || !PhoneUtil.isMobileNumber(phoneString)){
            showToast("请输入正确手机号");
            return false;
        }
        if(secretString.length()<6){
            showToast("请输入正确密码");
            return false;
        }
        return true;
    }
        private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode) {
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String code = getIntent().getStringExtra("code");
        if(code!=null && Constant.WX_LGOIN){
            getAccteeToken(code);
        }
    }
    /**
     * 获取微信AccessTOKEN
     * @param code
     */
    private void getAccteeToken(String code){
        showUpingDialog();
        Map<String ,String> map = new HashMap<>();
        map.put("appid",Constant.APP_ID);
        map.put("secret",Constant.APP_SECRET);
        map.put("code",code);
        map.put("grant_type","authorization_code");
        MyModel.getWXAccessToken(MyModel.getRetrofitService().getWXAccessToken(map), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                Map<String,String> map  = (Map<String, String>) data;
                getUserInfo(map.get("access_token"),map.get("openid"));
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
                closeUpingDialog();
            }

            @Override
            public void onError() {
            }

            @Override
            public void onComplete() {

            }
        });
    }
    /**
     * 获取微信用户信息
     */

    private void getUserInfo(String access_token, String openid) {
        Map<String ,String> map = new HashMap<>();
        map.put("access_token",access_token);
        map.put("openid",openid);
        MyModel.getUserInfo(MyModel.getRetrofitService().getUserInfo(map), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                //TODO 传入openID，服务器登录
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }
}
