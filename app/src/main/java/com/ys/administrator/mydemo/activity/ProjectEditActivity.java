package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.model.ProjectInfoBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.PhoneUtil;
import com.ys.administrator.mydemo.util.SPUtil;

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
    TextView etPjAddress;
    @BindView(R.id.etAddress)
    EditText etAddress;

    int typeid=-1,typefinalid=-1;
    String phone;
    String leader;
    String pjname;
    String gn;
    String area;
    String address;
    @BindView(R.id.tvPjType)
    TextView tvPjType;

    int id;
    @BindView(R.id.tvTZType)
    TextView tvTZType;

    ProjectInfoBean projectInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creat);
        ButterKnife.bind(this);
        initToolbar("项目基本信息");
        initData();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
        getData();
    }

    private void initData() {
        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            showToast("未获取到项目id");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_next, menu);
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
        String trim = etAddress.getText().toString().trim();
        address = TextUtils.isEmpty(trim)?etPjAddress.getText().toString().trim():etPjAddress.getText().toString().trim() +"-"+ trim;
//        if (TextUtils.isEmpty(phone) || !PhoneUtil.isMobileNumber(phone)) {
//            showToast("请输入正确的联系电话");
//            return false;
//        }
//        if (TextUtils.isEmpty(leader)) {
//            showToast("请输入负责人");
//            return false;
//        }
        if (TextUtils.isEmpty(pjname)) {
            showToast("请输入项目名称");
            return false;
        }
//        if (TextUtils.isEmpty(area)) {
//            showToast("请输入建筑面积");
//            return false;
//        }
//        if (TextUtils.isEmpty(gn)) {
//            showToast("请输入功能");
//            return false;
//        }
//        if (TextUtils.isEmpty(address)) {
//            showToast("请输入地址");
//            return false;
//        }
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

    private void getData() {
        showUpingDialog();
        MyModel.getNetData(mContext,MyModel.getRetrofitService().getPeojectDetail(MyModel.getRequestHeaderMap("/project/info"), id), new ICallBack<ProjectInfoBean>() {
            @Override
            public void onSuccess(ProjectInfoBean data) {
                projectInfoBean = (ProjectInfoBean) data;
                Log.d(TAG, "onSuccess: ");
                setDataInfo();
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onError() {closeUpingDialog(); }

            @Override
            public void onComplete() {
                closeUpingDialog();
            }
        });
    }

    private void setDataInfo() {
        if(projectInfoBean==null){
            return;

        }
        ProjectInfoBean.ProjectBean.InfoBean info = projectInfoBean.getProject().getInfo();
        etGn.setText(info.get功能());
        etPjName.setText(projectInfoBean.getProject().getName());
        etPjLeader.setText(info.get联系人());
        etPhone.setText(info.get电话());
        etPjarea.setText(info.get建筑面积());
        String ad = info.get地址();
        if(ad.length()>1){
            int i = ad.indexOf("-");
            if(i>0){
                etPjAddress.setText(ad.substring(0,i));
                etAddress .setText(ad.substring(i+1));
            }else {
                etPjAddress.setText(ad);
            }

        }else {
            etPjAddress.setText(ad);
        }
        typefinalid = projectInfoBean.getProject().getType();
        StatusListBean typeList = SPUtil.getTypeList();
        for (int i = 0; i <typeList.getList().size() ; i++) {
            for (int j = 0; j < typeList.getList().get(i).getTypes().size(); j++) {
                if(typeList.getList().get(i).getTypes().get(j).getId() == typefinalid){
                    tvPjType.setText(typeList.getList().get(i).getName());
                    tvTZType.setText(typeList.getList().get(i).getTypes().get(j).getName());
                    typeid = typeList.getList().get(i).getId();
                    break;
                }
            }
        }
    }

    private void saveData() {
        showUpingDialog();
        Map<String, Object> map = new HashMap<>();
        // 创建文件的参数
        map.put("type", typefinalid);
        map.put("id", id);
        map.put("name", pjname);
        Map<String, String> mapinfo = new HashMap<>();
        mapinfo.put("联系人", leader);
        mapinfo.put("电话", phone);
        mapinfo.put("功能", gn);
        mapinfo.put("建筑面积", area);
        mapinfo.put("地址", address);
        map.put("info", mapinfo);

        MyModel.getNetData(mContext,MyModel.getRetrofitService().editProject(MyModel.getRequestHeaderMap("/project/info"), MyModel.getJsonRequestBody(map)), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                showToast("编辑项目成功");
//                setResult(200);
//                finish();
                Bundle bundle = new Bundle();
                bundle.putInt("id",id);
                openActivityWithResult(UpLoadDataActivity.class,bundle,450);
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

    @OnClick({R.id.rxPjtype, R.id.rxTZtype, R.id.etPjAddress})
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
                if(typeid==0){
                    showToast("请重新选择项目类型");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("typeid",typeid);
                openActivityWithResult(ItemChoiseActivity.class, bundle, 125);
                break;
                case  R.id.etPjAddress:
                    openActivityWithResult(ProvinceActivity.class,null,114);
                    break;
        }
    }

    @Override
    protected void whenActivityFinish() {
        super.whenActivityFinish();
        setResult(200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 120 && resultCode == 200 && data != null) {
            typeid = data.getIntExtra("id", 0);
            tvPjType.setText(data.getStringExtra("name"));
            String trim = tvTZType.getText().toString().trim();
            if(typeid==1){
                if(trim.indexOf("土建")!=-1){
                    typefinalid = 1;
                }else if(trim.indexOf("装修")!=-1){
                    typefinalid = 3;
                }
            }else if(typeid==2){
                if(trim.indexOf("土建")!=-1){
                    typefinalid = 2;
                }else if(trim.indexOf("装修")!=-1){
                    typefinalid = 4;
                }
            }

        } else if (requestCode == 125 && resultCode == 200 && data != null) {
            typefinalid = data.getIntExtra("id", 0);
            tvTZType.setText(data.getStringExtra("name"));
        }else if (requestCode == 450 && resultCode == 200 ){//UpLoadDataActivity huidiao
            setResult(200);
            finish();
        }else if (requestCode == 114 && resultCode == 1 ){
            String provinceName = data.getStringExtra("provinceName");
            String cityName = data.getStringExtra("cityName");
            String areaName = data.getStringExtra("areaName");
            String address="";
            if(cityName.equals(provinceName)){
                address = cityName+areaName;
            }else {
                address =provinceName+ cityName+areaName;
            }
            etPjAddress.setText(address);
        }
    }

}
