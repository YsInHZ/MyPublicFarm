package com.ys.administrator.mydemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

public class ProjectCreatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creat);
        initToolbar("新建项目");
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    @Override
    public void showData(Object data) {

    }
}
