package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.DocAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.http.DownLoadUtils;
import com.ys.administrator.mydemo.model.DocBean;
import com.ys.administrator.mydemo.model.MsgListBean;
import com.ys.administrator.mydemo.util.FilePathUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsgDetialActivity extends BaseActivity {
    MsgListBean.PageBean msgBean;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.viewpoint)
    View viewpoint;
    @BindView(R.id.ivImg)
    ImageView ivImg;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvCon)
    TextView tvCon;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    List<DocBean.ListBean> lists;
    DocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detial);
        ButterKnife.bind(this);
        initView();
        initData();
        setReadMsg();
        initToolbar(msgBean.getTitle());
        setData();
    }

    private void setReadMsg() {
        MyModel.getNetData(mContext,MyModel.getRetrofitService().msgRead(MyModel.getRequestHeaderMap("/user/msg/read"), msgBean.getId()), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                setResult(200);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new DocAdapter(R.layout.item_doc,new ArrayList<>());
        recycler.setAdapter(adapter);
        adapter.setClickListener(new DocAdapter.btClick() {
            @Override
            public void downClick(String url,int [] pos) {
                downLoadFile(url,pos);
            }

            @Override
            public void poenClick(String url, int[] pos) {
                String s = FilePathUtil.getFilePathWithOutEnd() + url;
                FilePathUtil.openFiles(mContext,s);
                showToast("请到\n"+s+"下查看文件");
            }
        });
    }

    private void setData() {
        tvName.setText(msgBean.getTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(msgBean.getAt());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tvTime.setText(format.format(calendar.getTime()));
        tvCon.setText(msgBean.getBody());
        List<MsgListBean.PageBean.FilesBean> files = msgBean.getFiles();

        lists = new ArrayList<>();
        if(files!=null && files.size()>0){
            ivImg.setVisibility(View.VISIBLE);
            for (int i = 0; i < files.size(); i++) {
                DocBean.ListBean lb = new DocBean.ListBean(files.get(i).getName(),files.get(i).getUrl());
                lists.add(lb);
            }
            adapter.setNewData(lists);
        }else {
            ivImg.setVisibility(View.GONE);
        }
    }

    private void initData() {

        String data = getIntent().getStringExtra("data");
        msgBean = JSON.parseObject(data, MsgListBean.PageBean.class);
        if (msgBean == null) {
            showToast("获取消息失败");
            finish();
        }
    }
    private void downLoadFile(String url,int[] pos){
        DownLoadUtils instance = DownLoadUtils.getInstance();
        instance.downloadFile(url, pos,new DownLoadUtils.DownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int currentLength, int[] pos) {
                runOnUiThread(() -> {
                    lists.get(pos[0]).setDownLoad(true);
                    lists.get(pos[0]).setProgress(currentLength);
                    adapter.notifyDataSetChanged();
                });

            }

            @Override
            public void onFinish(String localPath, int[] pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("文件已下载到"+localPath);
                        lists.get(pos[0]).setDownLoad(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void showData(Object data) {

    }
}
