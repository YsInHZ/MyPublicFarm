package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpLoadDataActivity extends BaseActivity {

    @BindView(R.id.tvTypeName)
    TextView tvTypeName;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_data);
        ButterKnife.bind(this);
        initToolbar("上传资料");
        initView();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick({R.id.rlChoiseType, R.id.tvNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlChoiseType:
                break;
            case R.id.tvNext:
                break;
        }
    }
}
