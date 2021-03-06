package com.ys.administrator.mydemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.presenter.CommonPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFileActivity extends BaseActivity {
    RxPermissions rxPermissions;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_file);
        ButterKnife.bind(this);
        initToolbar("我的文件");
        initData();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
          rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings
                    }
                });
    }

    private void initData() {
         data = getIntent().getStringExtra("data");
    }

    @Override
    public void showData(Object data) {

    }
    private boolean checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    @OnClick({R.id.rlQQFile, R.id.rlWXfile, R.id.rlOtherFile,R.id.rlDownLoadfile,R.id.rlWXvoid})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rlQQFile:
                if(checkPermission())
                    bundle.putString("type",MineFileListActivity.FILE_QQ);
                break;
            case R.id.rlWXfile:
                if(checkPermission())
                    bundle.putString("type",MineFileListActivity.FILE_WX);
                break;
            case R.id.rlWXvoid:
                if(checkPermission())
                    bundle.putString("type",MineFileListActivity.FILE_WX_VOID);
                break;
            case R.id.rlOtherFile:
                if(checkPermission())
                    bundle.putString("type",MineFileListActivity.FILE_OTHER);
                break;
            case R.id.rlDownLoadfile:
                if(checkPermission())
                    bundle.putString("type",MineFileListActivity.FILE_DOWN_LOAD);
                break;
        }
        if(!TextUtils.isEmpty(data)){
            bundle.putString("data",data);
            openActivityWithResult(MineFileListActivity.class,bundle,101);
        }else {
            openActivity(MineFileListActivity.class,bundle);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==101 &&resultCode==200 && data!=null ){
            setResult(200,data);
            finish();
        }
    }
}
