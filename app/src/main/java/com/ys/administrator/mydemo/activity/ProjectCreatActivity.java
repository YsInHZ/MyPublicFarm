package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.ProjectCreatBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.PhoneUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectCreatActivity extends BaseActivity {

    @BindView(R.id.etPjName)
    EditText etPjName;
    @BindView(R.id.etPjLeader)
    EditText etPjLeader;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etGn)
    EditText etGn;
    @BindView(R.id.etPjarea)
    EditText etPjarea;
    @BindView(R.id.etPjAddress)
    EditText etPjAddress;

    int typeid=-1,typefinalid=-1;
    String phone;
    String leader;
    String pjname;
    String gn;
    String area;
    String address;
    @BindView(R.id.tvPjType)
    TextView tvPjType;
    @BindView(R.id.tvTZType)
    TextView tvTZType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creat);
        ButterKnife.bind(this);
        initToolbar("新建项目");
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (checkInput()) {
            saveData();
        }
        return false;
    }

    private void saveData() {
        showUpingDialog();
        Map<String, Object> map = new HashMap<>();
        // 创建文件的参数
        map.put("type", typefinalid);
        map.put("name", pjname);
        Map<String, String> mapinfo = new HashMap<>();
        mapinfo.put("联系人", leader);
        mapinfo.put("电话", phone);
        mapinfo.put("功能", gn);
        mapinfo.put("建筑面积", area);
        mapinfo.put("地址", address);
        map.put("info", mapinfo);

        MyModel.getNetData(MyModel.getRetrofitService().creatProject(MyModel.getRequestHeaderMap("/project"), MyModel.getJsonRequestBody(map)), new ICallBack<ProjectCreatBean>() {
            @Override
            public void onSuccess(ProjectCreatBean data) {
                showToast("创建项目成功");
                Intent intent = new Intent();
                intent.putExtra("id",data.getProject().getId());
                setResult(200,intent);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }

            @Override
            public void onError() {
                closeUpingDialog();
            }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }

    //    检查输入
    private boolean checkInput() {
        phone = etPhone.getText().toString().trim();
        leader = etPjLeader.getText().toString().trim();
        pjname = etPjName.getText().toString().trim();
        gn = etGn.getText().toString().trim();
        area = etPjarea.getText().toString().trim();
        address = etPjAddress.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !PhoneUtil.isMobileNumber(phone)) {
            showToast("请输入正确的联系电话");
            return false;
        }
        if (TextUtils.isEmpty(leader)) {
            showToast("请输入负责人");
            return false;
        }
        if (TextUtils.isEmpty(pjname)) {
            showToast("请输入项目名称");
            return false;
        }
        if (TextUtils.isEmpty(area)) {
            showToast("请输入建筑面积");
            return false;
        }
        if (TextUtils.isEmpty(gn)) {
            showToast("请输入功能");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            showToast("请输入地址");
            return false;
        }
        if (typeid == -1) {
            showToast("请选择项目类型");
            return false;
        }
        if (typefinalid == -1) {
            showToast("请选择项目土建/装修");
            return false;
        }
        return true;
    }

    @Override
    public void showData(Object data) {

    }

//    @OnClick({R.id.rxPjtype,R.id.rxTZtype})
//    public void onViewClicked() {
//        switch ()
//        openActivityWithResult(ItemChoiseActivity.class, null, 120);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 120 && resultCode == 200 && data != null) {
            typeid = data.getIntExtra("id", 0);
            tvPjType.setText(data.getStringExtra("name"));
        }else if(requestCode == 125 && resultCode == 200 && data != null){
            typefinalid = data.getIntExtra("id", 0);
            tvTZType.setText(data.getStringExtra("name"));
        }
    }

    @OnClick({R.id.rxPjtype, R.id.rxTZtype})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxPjtype:
                openActivityWithResult(ItemChoiseActivity.class, null, 120);
                break;
            case R.id.rxTZtype:
                if(typeid==-1){
                    showToast("请先选择项目类型");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("typeid",typeid);
                openActivityWithResult(ItemChoiseActivity.class, bundle, 125);
                break;
        }
    }
}
