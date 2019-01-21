package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ContactAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineContactActivity extends BaseActivity {

    @BindView(R.id.rvList)
    RecyclerView rvList;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_contact);
        ButterKnife.bind(this);
        initToolbar("客服咨询", true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        initView();
    }

    private void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(R.layout.item_contact,new ArrayList<String>(){{add("");add("");add("");add("");}});
        rvList.setAdapter(contactAdapter);
    }

    @Override
    public void showData(Object data) {

    }
}
