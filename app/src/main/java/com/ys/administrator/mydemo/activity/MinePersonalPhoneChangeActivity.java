package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MinePersonalPhoneChangeActivity extends BaseActivity {

    @BindView(R.id.tvOld)
    TextView tvOld;
    @BindView(R.id.etNew)
    EditText etNew;
    @BindView(R.id.etCode)
    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_personal_phone_change);
        ButterKnife.bind(this);

        initToolbar("手机换绑", true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change) {
            //TODO
        }
        return false;
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick(R.id.bCode)
    public void onViewClicked() {
    }
}
