package com.ys.administrator.mydemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initToolbar("关于我们");
    }

    @Override
    public void showData(Object data) {

    }
}
