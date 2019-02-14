package com.ys.administrator.mydemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.FileListAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.FilePathUtil;
import com.ys.administrator.mydemo.util.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFileListActivity extends BaseActivity {
    public static final String FILE_QQ = "FILE_QQ";//
    public static final String FILE_WX = "FILE_WX";
    public static final String FILE_OTHER = "FILE_OTHER";
    public static final String FILE_DOWN_LOAD = "FILE_DOWN_LOAD";
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private String fileType = null;
    private String title = "";
    private List<File> files;
    FileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_file_list);
        ButterKnife.bind(this);
        initData();
        initToolbar(title);
        initView();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileListAdapter(R.layout.item_mine_list_file,files);
        recycler.setAdapter(adapter);
    }

    private void initData() {
        fileType = getIntent().getStringExtra("type");
        if (fileType == null || fileType.isEmpty()) {
            showToast("未获取到类型");
            finish();
        }
        String s = Environment.getExternalStorageDirectory().toString();
        String path = "";

        if (FILE_QQ.equals(fileType)) {
            title = "QQ下载文件";
            path = s + "/tencent/QQfile_recv";
            files = FilePathUtil.readFiles(path);
        } else if (FILE_WX.equals(fileType)) {
            title = "微信下载文件";
            path = s + "/tencent/MicroMsg/Download";
            files = FilePathUtil.readFiles(path);
        } else if (FILE_OTHER.equals(fileType)) {
            title = "其他文件";
            files = new ArrayList<>();
            List<String> localFileList = SPUtil.getLocalFileList();
            if(localFileList!=null){
                for (int i = 0; i <localFileList.size() ; i++) {
                    if(!TextUtils.isEmpty(localFileList.get(i))){
                        files.add(new File(localFileList.get(i)));
                    }

                }
            }

        }else if(FILE_DOWN_LOAD.equals(fileType)){
            title = "项目下载文件";
            //TODO 已下载的项目列表遍历
            //需要以文件为item的适配器，每点击item如果为文件夹则遍历文件夹更新列表
        } else {
            finish();
        }
    }

    @Override
    public void showData(Object data) {

    }
}
