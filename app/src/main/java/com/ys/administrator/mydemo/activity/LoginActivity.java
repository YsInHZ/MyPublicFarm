package com.ys.administrator.mydemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.model.UserInfoBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.NavigationBarUtil;
import com.ys.administrator.mydemo.util.PhoneUtil;
import com.ys.administrator.mydemo.util.SPUtil;
import com.ys.administrator.mydemo.util.StatusbarUtils;
import com.ys.administrator.mydemo.util.WeiXinUtil;

import java.io.File;
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
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        interceptKeyBack = true;
        finishAllWithOut(LoginActivity.class);
        /**
         * ---------------------------------text------------------------------
         */

        checkPermission();
        String s = Environment.getExternalStorageDirectory().toString();
//        String s = Environment.getDataDirectory().toString();
        String path = s+"/tencent/QQfile_recv";
//        path = s;
        Log.d(TAG, "QQ下载文件");
//        readFiles(path);
        Log.d(TAG, "微信下载文件");
        path = s+"/tencent/MicroMsg/Download";
//        readFiles(path);
    }

    private void readFiles(String path) {
        File file = new File(path);
        Log.d(TAG, "path:"+path+"  exist:"+file.exists());
        if(file.exists() && file.isDirectory()){
            String[] files = file.list();
            if(files!=null)
            for (int i = 0; i < files.length; i++) {
                Log.d(TAG, files[i]+"");
            }
            Log.d(TAG, "finish");
        }
    }


    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void showData(Object data) {

    }


    @OnClick({R.id.tvLogin, R.id.ivWei, R.id.tvRergest, R.id.tvForget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                if(verifyClickTime() && checkInput()){
                    // 网络请求进行登录
                    getLogin();
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

    private void getLogin() {
        showUpingDialog();
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phoneString);
        map.put("passwd",secretString);
        MyModel.getNetData(MyModel.getRetrofitService().getLogin(MyModel.getJsonRequestBody(map)), new ICallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                SPUtil.saveUserInfo(data.getUser());
                openActivity(IndexActivity.class);
                Log.d(TAG, "onSuccess: ");
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
