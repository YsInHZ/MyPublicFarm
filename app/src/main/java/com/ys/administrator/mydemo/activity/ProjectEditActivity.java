package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.ProjectInfoBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.PhoneUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectEditActivity extends BaseActivity {

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

    int typeid;
    String phone;
    String leader;
    String pjname;
    String gn;
    String area;
    String address;
    @BindView(R.id.tvPjType)
    TextView tvPjType;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creat);
        ButterKnife.bind(this);
        initToolbar("编辑项目");
        initData();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    private void initData() {
         id = getIntent().getIntExtra("id", -1);
         if(id==-1){
             showToast("未获取到项目id");
             finish();
         }
        String data = getIntent().getStringExtra("data");
        ProjectInfoBean.ProjectBean.InfoBean infoBean = JSON.parseObject(data, ProjectInfoBean.ProjectBean.InfoBean.class);
        etGn.setText(infoBean.get功能());
        etPjName.setText( getIntent().getStringExtra("name"));
        etPjLeader.setText(infoBean.get联系人());
        etPhone.setText(infoBean.get电话());
        etPjarea.setText(infoBean.get建筑面积());
        etPjAddress.setText(infoBean.get地址());
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
        if (typeid==0) {
            showToast("请选择项目类型");
            return false;
        }
        return true;
    }
    private void saveData() {
        showUpingDialog();
        Map<String, Object> map = new HashMap<>();
        // 创建文件的参数
        map.put("type",typeid);
        map.put("id",id);
        map.put("name",pjname);
        Map<String,String> mapinfo = new HashMap<>();
        mapinfo.put("联系",leader);
        mapinfo.put("电话",phone);
        mapinfo.put("功能",gn);
        mapinfo.put("建筑面积",area);
        mapinfo.put("地址",address);
        map.put("info",mapinfo);

        MyModel.getNetData(MyModel.getRetrofitService().editProject(MyModel.getRequestHeaderMap("/project/info"), MyModel.getJsonRequestBody(map)), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                showToast("编辑项目成功");
                setResult(200);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
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
    @OnClick(R.id.rxPjtype)
    public void onViewClicked() {
        openActivityWithResult(ItemChoiseActivity.class, null, 120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 120 && resultCode == 200 && data != null) {
            typeid = data.getIntExtra("id", 0);
            tvPjType.setText(data.getStringExtra("name"));
        }
    }
}
