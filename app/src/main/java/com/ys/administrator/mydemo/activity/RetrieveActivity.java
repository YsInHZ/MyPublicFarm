package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.model.UserInfoBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.PhoneUtil;
import com.ys.administrator.mydemo.util.SPUtil;
import com.ys.administrator.mydemo.util.StatusbarUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 * 忘记密码
 */
public class RetrieveActivity extends BaseActivity {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etSecreat)
    EditText etSecreat;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvSendCode)
    TextView tvSendCode;
    @BindView(R.id.tvRetrieve)
    TextView tvRetrieve;

    String phoneString;
    String secretString;
    String codeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_retrieve);
        ButterKnife.bind(this);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick({R.id.tvSendCode, R.id.tvRetrieve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSendCode:
                // 发送验证码
                if(checkInput()){
                    countDown();
                    sendCode();
                }
                break;
            case R.id.tvRetrieve:
                // 找回
                if(checkAllInput() && verifyClickTime()){
                    retrieve();
                }
                break;
        }
    }
    private void  retrieve() {
        showUpingDialog();
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phoneString);
        map.put("passwd",secretString);
        map.put("smsCode",codeString);

        MyModel.getNetData(mContext,MyModel.getRetrofitService().getSetPasswd(MyModel.getJsonRequestBody(map)), new ICallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                Log.d(TAG, "onSuccess: ");

                //  保存登录成功用户信息
                // 跳转主页面
                SPUtil.saveUserInfo(data.getUser());
                openActivity(IndexActivity.class);
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
                showToast(msg);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
                closeUpingDialog();
            }
        });
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        MyModel.getNetData(mContext,MyModel.getRetrofitService().getSmsCode(phoneString), new ICallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean data) {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
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
    //检查手机
    private boolean checkInput() {
        phoneString = etPhone.getText().toString().trim();
        if(phoneString.length()!=11 || !PhoneUtil.isMobileNumber(phoneString)){
            showToast("请输入正确手机号");
            return false;
        }
        return true;
    }
    private boolean checkAllInput(){
        codeString = etCode.getText().toString().trim();
        secretString = etSecreat.getText().toString().trim();
        if(codeString.length()<4){
            showToast("请输入正确验证码");
            return false;
        }
        if(secretString.length()<6){
            showToast("密码长度不能小于6位");
            return false;
        }
        return  checkInput();

    }

    //倒计时
    private void countDown(){
        tvSendCode.setEnabled(false);
        CountDownTimer downTimer = new CountDownTimer(60000,1000) {

            int cd = 60;
            @Override
            public void onTick(long millisUntilFinished) {
                tvSendCode.setText(cd+"s");
                cd--;
            }

            @Override
            public void onFinish() {
                tvSendCode.setText("重新发送");
                tvSendCode.setEnabled(true);
            }
        };
        downTimer.start();
    }
}
