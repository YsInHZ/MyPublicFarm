package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ItemChoisTZeAdapter;
import com.ys.administrator.mydemo.adapter.ItemChoiseAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.SPUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemChoiseActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    ItemChoiseAdapter adapter;
    ItemChoisTZeAdapter adaptertz;
    StatusListBean data;
    int typeid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_choise);
        ButterKnife.bind(this);
        initToolbar("选择");
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        typeid = getIntent().getIntExtra("typeid",-1);
        initRecycler();
        getTypeList();
    }

    private void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ItemChoiseAdapter(R.layout.item_choise,new ArrayList<>());
        adaptertz = new ItemChoisTZeAdapter(R.layout.item_choise,new ArrayList<>());
        if(typeid==-1){
            recycler.setAdapter(adapter);
        }else {
            recycler.setAdapter(adaptertz);
        }

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                i.putExtra("id",data.getList().get(position).getId());
                i.putExtra("name",data.getList().get(position).getName());
                setResult(200,i);
                finish();
            }
        });
        adaptertz.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                StatusListBean.ListBean.TypesBean item = (StatusListBean.ListBean.TypesBean) adapter.getItem(position);
                i.putExtra("id",item.getId());
                i.putExtra("name",item.getName());
                setResult(200,i);
                finish();
            }
        });

    }
    private void getTypeList() {
        showUpingDialog();
        MyModel.getNetData(MyModel.getRetrofitService().getTypeList(), new ICallBack<StatusListBean>() {
            @Override
            public void onSuccess(StatusListBean data) {
                ItemChoiseActivity.this.data = data;
                if(typeid==-1){
                    adapter.setNewData(data.getList());
                }else {
                    for (int i = 0; i < data.getList().size(); i++) {
                        if( data.getList().get(i).getId()==typeid){
                            adaptertz.setNewData(data.getList().get(i).getTypes());
                            break;
                        }
                    }

                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }
    @Override
    public void showData(Object data) {

    }
}
