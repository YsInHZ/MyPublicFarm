package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.DocAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.http.DownLoadUtils;
import com.ys.administrator.mydemo.model.DocBean;
import com.ys.administrator.mydemo.util.FilePathUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocDownLoadActivity extends BaseActivity {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    DocAdapter adapter;
    DocBean docBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_down_load);
        ButterKnife.bind(this);
        initToolbar("文档模板");
        initRecycle();
        getData();
    }

    private void getData() {
        showUpingDialog();
        MyModel.getNetData(mContext,MyModel.getRetrofitService().getDocList(), new ICallBack<DocBean>() {
            @Override
            public void onSuccess(DocBean data) {
                docBean = data;
                adapter.setNewData(docBean.getList());
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }

    private void initRecycle() {
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
    private void downLoadFile(String url,int[] pos){
        DownLoadUtils instance = DownLoadUtils.getInstance();
        instance.downloadFile(url, pos,new DownLoadUtils.DownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int currentLength, int[] pos) {
                runOnUiThread(() -> {
                    docBean.getList().get(pos[0]).setDownLoad(true);
                    docBean.getList().get(pos[0]).setProgress(currentLength);
                    adapter.notifyDataSetChanged();
                });

            }

            @Override
            public void onFinish(String localPath, int[] pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("文件已下载到"+localPath);
                        docBean.getList().get(pos[0]).setDownLoad(false);
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
