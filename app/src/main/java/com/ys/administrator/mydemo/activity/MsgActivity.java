package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.MsgListAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.MsgListBean;
import com.ys.administrator.mydemo.util.RefreshUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    MsgListAdapter adapter;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    MsgListBean msgListBean;
    boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        initToolbar("消息中心");
        initView();
        getData(-1);
    }

    private void getData(int id) {
        if (id > 0) {
            MyModel.getNetData(MyModel.getRetrofitService().getMsg(MyModel.getRequestHeaderMap("/user/msg"), id), iCallBack);
        } else {
            MyModel.getNetData(MyModel.getRetrofitService().getMsg(MyModel.getRequestHeaderMap("/user/msg")), iCallBack);

        }
    }

    ICallBack iCallBack = new ICallBack<MsgListBean>() {
        @Override
        public void onSuccess(MsgListBean data) {
            if(isRefresh){
                msgListBean = data;
            }else {
                if( msgListBean.getPage()!=null){
                    msgListBean.getPage().addAll(data.getPage());
                }else {
                    msgListBean.setPage(data.getPage());
                }

            }
            adapter.setNewData(msgListBean.getPage());
        }

        @Override
        public void onFailure(String msg) {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onComplete() {

        }
    };

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MsgListAdapter(R.layout.item_msg, new ArrayList<>());
        recycler.setAdapter(adapter);
        RefreshUtil.setRefreshHead(getContext(), refreshLayout);
        RefreshUtil.setLoadBottom(getContext(), refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isRefresh =  true;
                        getData(-1);
                    }
                });
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isRefresh =  false;
                        if(msgListBean!=null && msgListBean.getPage()!=null&& msgListBean.getPage().size()>0){
                            getData(msgListBean.getPage().get(msgListBean.getPage().size()-1).getId());
                        }
                    }
                });

            }
        });
    }

    @Override
    public void showData(Object data) {

    }
}
