package com.example.administrator.mydemo.activity;

import android.os.Bundle;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.base.BaseActivity;
import com.example.administrator.mydemo.presenter.CommonPresenter;
import com.example.administrator.mydemo.view.CommonView;

public class LoginActivity extends BaseActivity implements CommonView {

    CommonView commonView;
    CommonPresenter commonPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonPresenter.dettachView();
    }

    @Override
    public void showData(Object data) {

    }
}
