package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ItemChoiseAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressChoiseActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    ItemChoiseAdapter adapter;
    List<StatusListBean.ListBean> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_choise);
        ButterKnife.bind(this);
        initToolbar("选择");
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        initData();
        initRecycler();
    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        boolean isSmall = "small".equals(data);
        String list = getIntent().getStringExtra("list");
        lists = new ArrayList<>();
        List<String> strings = JSONArray.parseArray(list, String.class);
        for (int i = 0; i < strings.size(); i++) {
            if(isSmall &&  strings.get(i).indexOf("图审上传资料")!=-1 ){
                continue;
            }
            lists.add(new StatusListBean.ListBean(strings.get(i)));
        }


    }

    private void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ItemChoiseAdapter(R.layout.item_choise,lists);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                i.putExtra("name",lists.get(position).getName());
                setResult(200,i);
                finish();
            }
        });
    }

    @Override
    public void showData(Object data) {

    }
}
