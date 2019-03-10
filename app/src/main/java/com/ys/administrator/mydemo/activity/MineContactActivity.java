package com.ys.administrator.mydemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ContactAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.ConnectBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.ClipBoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 咨询
 */
public class MineContactActivity extends BaseActivity {

    @BindView(R.id.rvList)
    RecyclerView rvList;
    ContactAdapter contactAdapter;
    List<Map<String, String>> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_contact);
        ButterKnife.bind(this);
        initToolbar("客服咨询", true);
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        initView();
        getContact();
    }

    private void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        lists = new ArrayList<>();
        contactAdapter = new ContactAdapter(R.layout.item_contact, lists);
        rvList.setAdapter(contactAdapter);
        contactAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Map<String, String> item = (Map<String, String>) adapter.getItem(position);
                ClipBoardUtil.CopyToClipboard(mContext, item.get("name"));
                showToast("已复制到粘贴板");
                return false;
            }
        });
        contactAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Map<String, String> item = (Map<String, String>) adapter.getItem(position);
                if ("phone".equals(item.get("type"))) {
                    // 拨打电话
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + item.get("name"));
                    intent.setData(data);
                    startActivity(intent);

                }
            }
        });
    }

    private void getContact(){
        MyModel.getNetData(mContext,MyModel.getRetrofitService().getContact(), new ICallBack<ConnectBean>() {
            @Override
            public void onSuccess(ConnectBean data) {
                Map<String,String> qqmap = new HashMap<>();
                Map<String,String> wxmap = new HashMap<>();
                Map<String,String> phmap = new HashMap<>();
                qqmap.put("type","qq");
                wxmap.put("type","wchat");
                phmap.put("type","phone");
                qqmap.put("name",data.getContact().getQq());
                wxmap.put("name",data.getContact().getWechat());
                phmap.put("name",data.getContact().getMobile());
                lists.add(qqmap);
                lists.add(wxmap);
                lists.add(phmap);
                contactAdapter.setNewData(lists);
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
        });
    }
    @Override
    public void showData(Object data) {

    }
}
