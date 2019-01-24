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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人资料
 */
public class MinePersonalDataActivity extends BaseActivity {

    @BindView(R.id.ciHead)
    CircleImageView ciHead;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPhone)
    TextView etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_personal_data);
        ButterKnife.bind(this);
        initToolbar("个人资料",true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save){
            //TODO
        }
        return false;
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick(R.id.bChange)
    public void onViewClicked() {
        openActivity(MinePersonalPhoneChangeActivity.class);
    }
}
