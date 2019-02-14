package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.PhoneUtil;
import com.ys.administrator.mydemo.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更换手机
 */
public class MinePersonalPhoneChangeActivity extends BaseActivity {

    @BindView(R.id.tvOld)
    TextView tvOld;
    @BindView(R.id.etNew)
    EditText etNew;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.bCode)
    Button bCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_personal_phone_change);
        ButterKnife.bind(this);

        initToolbar("手机换绑", true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        initData();
    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        tvOld.setText(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change) {
            if(checkInput()){
                setMobile();
            }
        }
        return false;
    }

    private void setMobile() {
        showUpingDialog();
        Map<String,String> map = new HashMap<>();
        map.put("mobile",etNew.getText().toString().trim());
        map.put("smsCode",etCode.getText().toString().trim());
        MyModel.getNetData(MyModel.getRetrofitService().setMobile(MyModel.getRequestHeaderMap("/user/setMobile"),MyModel.getJsonRequestBody(map)), new ICallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean data) {
                Log.d(TAG, "onSuccess: ");
                showToast("更换绑定手机号码成功，请重新登录");
                Constant.clearUserInfo();
                SPUtil.clearStore();
                openActivity(LoginActivity.class);
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
                closeUpingDialog();
            }
        });
    }

    private boolean checkInput() {
        String trim = etNew.getText().toString().trim();
        if (trim.isEmpty() || trim.length() != 11 || !PhoneUtil.isMobileNumber(trim)) {
            showToast("请输入正确手机号");
            return false;
        }
        if(etCode.getText().toString().trim().isEmpty() || etCode.getText().toString().trim().length()!=6){
            showToast("请输入完整验证码");
            return false;
        }
        return true;
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick(R.id.bCode)
    public void onViewClicked() {
        String trim = etNew.getText().toString().trim();
        if (trim.isEmpty() || trim.length() != 11 || !PhoneUtil.isMobileNumber(trim)) {
            showToast("请输入正确手机号");
            return;
        }
        countDown();
        sendCode(trim);

    }

    private void countDown() {
        bCode.setEnabled(false);
        CountDownTimer downTimer = new CountDownTimer(60000, 1000) {

            int cd = 60;

            @Override
            public void onTick(long millisUntilFinished) {
                bCode.setText(cd + "s");
                cd--;
            }

            @Override
            public void onFinish() {
                bCode.setText("重新发送");
                bCode.setEnabled(true);
            }
        };
        downTimer.start();
    }

    private void sendCode(String phoneString) {
        MyModel.getNetData(MyModel.getRetrofitService().getSmsCode(phoneString), new ICallBack<BaseBean>() {
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
}
