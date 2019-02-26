package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import butterknife.OnClick;

public class MineFileListActivity extends BaseActivity {
    public static final String FILE_QQ = "FILE_QQ";//
    public static final String FILE_WX = "FILE_WX";
    public static final String FILE_OTHER = "FILE_OTHER";
    public static final String FILE_DOWN_LOAD = "FILE_DOWN_LOAD";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tvLocalPath)
    TextView tvLocalPath;
    @BindView(R.id.llLocal)
    LinearLayout llLocal;

    private String fileType = null;
    private String title = "";
    private List<File> files;
    FileListAdapter adapter;
    String data;
    String currentPath;//当前点击的文件夹路径（只有当FILE_DOWN_LOAD时用到）

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
        adapter = new FileListAdapter(R.layout.item_mine_list_file, files);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                File item = (File) adapter.getItem(position);
                if (FILE_DOWN_LOAD.equals(fileType) && item.isDirectory()) {//当前为已下载文件浏览  且 当前文件为文件夹
                    currentPath = item.getAbsolutePath();
                    String substring = currentPath.substring(FilePathUtil.getFilePath().length());
                    String replace = substring.replace("/", ">");
                    tvLocalPath.setText("根目录>MyProjece>"+replace);
                    files = FilePathUtil.readFilesWithDirectory(item.getAbsolutePath());
                    MineFileListActivity.this.adapter.setNewData(files);
                } else if (!TextUtils.isEmpty(data)) {
                    Intent intent = new Intent();
                    intent.putExtra("data", (item).getAbsolutePath());
                    setResult(200, intent);
                    finish();
                }
            }
        });


    }

    private void initData() {
        fileType = getIntent().getStringExtra("type");
        if (fileType == null || fileType.isEmpty()) {
            showToast("未获取到类型");
            finish();
        }
        String s = Environment.getExternalStorageDirectory().toString();
        String path = "";
        llLocal.setVisibility(View.GONE);
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
            if (localFileList != null) {
                for (int i = 0; i < localFileList.size(); i++) {
                    if (!TextUtils.isEmpty(localFileList.get(i))) {
                        files.add(new File(localFileList.get(i)));
                    }

                }
            }

        } else if (FILE_DOWN_LOAD.equals(fileType)) {
            llLocal.setVisibility(View.VISIBLE);
            title = "项目下载文件";
            // 已下载的项目列表遍历
            //需要以文件为item的适配器，每点击item如果为文件夹则遍历文件夹更新列表
            files = FilePathUtil.readFilesWithDirectory(FilePathUtil.getFilePath() + "project");
            currentPath = FilePathUtil.getFilePath() + "project";
            tvLocalPath.setText("根目录>MyProjece>projcet>");
        } else {
            finish();
        }
        data = getIntent().getStringExtra("data");
    }

    @Override
    public void showData(Object data) {

    }

    @OnClick(R.id.tvBack)
    public void onViewClicked() {
        if(currentPath.equals(FilePathUtil.getFilePath() + "project")){
            return;
        }
        File file = new File(currentPath);
        currentPath = file.getParent();
        String substring = currentPath.substring(FilePathUtil.getFilePath().length());
        String replace = substring.replace("/", ">");
        tvLocalPath.setText("根目录>MyProjece>"+replace);
        files = FilePathUtil.readFilesWithDirectory(currentPath);
        MineFileListActivity.this.adapter.setNewData(files);
    }
}
