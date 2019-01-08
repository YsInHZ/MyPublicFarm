package com.example.administrator.mydemo.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.base.BaseActivity;
import com.example.administrator.mydemo.presenter.CommonPresenter;
import com.example.administrator.mydemo.util.PhoneUtil;
import com.example.administrator.mydemo.util.StatusbarUtils;

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
                //TODO 发送验证码
                if(checkInput()){
                    countDown();
                }
                break;
            case R.id.tvRetrieve:
                //TODO 找回
                if(checkAllInput() && verifyClickTime()){

                }
                break;
        }
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
