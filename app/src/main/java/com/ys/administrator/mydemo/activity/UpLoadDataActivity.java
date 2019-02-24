package com.ys.administrator.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.RestaurantMenuRightAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.model.FileInfoModel;
import com.ys.administrator.mydemo.model.FileListDataBean;
import com.ys.administrator.mydemo.model.ProjectInfoBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.ys.administrator.mydemo.util.MediaTypeUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ----------装修项目基础资料-----------
 * 营业执照名称或预核名
 * 原始建筑图纸
 * 租赁合同
 * 建筑消防验收意见书
 * 建筑合法性证明文件
 * 建筑功能改变文件
 * 现场照片或视频
 * --------------------图审上报资料---------
 * 装修设计合同
 * 授权委托书
 * 委托人身份证复印件
 * 项目技术复核表
 * 项目投资金额
 * 项目立项文件、批文或备案文件
 * 建设用地规划许可证
 * 规划红线图及规划部门盖章的总平面图
 * 建设项目规划条件及面积预测绘报告
 * 初步设计批复或会议纪要
 * 建筑节能审查意见书及附节能评估报告书（表）或节能登记表
 * 工程地址勘察报告及外业见证报告
 * 勘察和设计资质证书复印件（非本省勘察设计单位提供进浙备案）
 * 勘察和设计合同复印件
 * 建筑设计合同
 * 项目投资金额
 * ---------------------土建图纸--------------
 * 建筑
 * 给排水
 * 电气
 * 暖通
 * 结构（含计算书）
 * 节能（含设计表、自评表）
 * ----------装修图纸-----------
 * 装饰
 * 给排水
 * 电气
 * 暖通
 * 结构（含计算书）
 * --------其他资料----------
 * 其他资料
 */

/**
 * TODO 首先获取项目资料详情，
 *      然后显示项目资料详情
 */
public class UpLoadDataActivity extends BaseActivity {

    @BindView(R.id.tvTypeName)
    TextView tvTypeName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    RestaurantMenuRightAdapter adapter;
    MyFillDialog choiseWayDialog;
    int pos;
    List<FileListDataBean> baseinfolists,repotrinfolists,buildinfolists,fitmentinfolists,otherinfolists;
    String[] baseinfo,repotrinfo,buildinfo,fitmentinfo,otherinfo;
    int id;
    ProjectInfoBean projectInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_data);
        ButterKnife.bind(this);
        initToolbar("上传资料");
        initData();
        getData();
        initView();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    /**
     * 获取项目详情数据
     */
    private void getData() {
        showUpingDialog();
        MyModel.getNetData(MyModel.getRetrofitService().getPeojectDetail(MyModel.getRequestHeaderMap("/project/info"), id), new ICallBack<ProjectInfoBean>() {
            @Override
            public void onSuccess(ProjectInfoBean data) {
                projectInfoBean = data;
                Log.d(TAG, "onSuccess: ");
                setDataInfo();
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
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

    /**
     * 读取项目详情-各个资料类别数据
     */
    private void setDataInfo() {
        Map<String, Object> data = projectInfoBean.getProject().getData();
//        data.get("工程基本信息");
        Map<String, Object> zxxm = getMapObject(data, "装修项目基础资料");
        Map<String, Object> tssb = getMapObject(data, "图审上报资料");
        Map<String, Object> tjtz = getMapObject(data, "土建图纸");
        Map<String, Object> zxtz = getMapObject(data, "装修图纸");
        Map<String, Object> qtzl = getMapObject(data, "其他资料");

        baseinfolists = setListFile(baseinfo,zxxm);
        repotrinfolists = setListFile(repotrinfo,tssb);
        buildinfolists = setListFile(buildinfo,tjtz);
        fitmentinfolists = setListFile(fitmentinfo,zxtz);
        otherinfolists = setListFile(otherinfo,qtzl);

        if(tvTypeName.getText().toString().trim().isEmpty()){
            tvTypeName.setText("装修项目基础资料");
        }
        try {
            String trim = tvTypeName.getText().toString().trim();
            switch (trim){
                case "装修项目基础资料":
                    adapter.setData(baseinfolists);
                    break;
                case "图审上报资料":
                    adapter.setData(repotrinfolists);
                    break;
                case "土建图纸":
                    adapter.setData(buildinfolists);
                    break;
                case "装修图纸":
                    adapter.setData(fitmentinfolists);
                    break;
                case "其他资料":
                    adapter.setData(otherinfolists);
                    break;
            }

        }catch (Exception e){
            Log.d(TAG, "setDataInfo: ");
        }
        

//        baseinfolists = new ArrayList<>();
//        for (String name:baseinfo) {
//            FileListDataBean fileListDataBean = new FileListDataBean(name);
//            List<FileInfoModel> files = getFiles(zxxm, name);
//            if(files!=null&& files.size()>0){
//                fileListDataBean.addFilePath(files);
//            }
//            baseinfolists.add(fileListDataBean);
//        }


    }

    /**
     * 通过key获取map中的map
     * @param map
     * @param key
     * @return
     */
    private Map<String, Object>  getMapObject(Map<String, Object> map ,String key){
        Object o = map.get(key);
        if(o==null){
            return null;
        }
        Map<String, Object> ss = (Map<String, Object>) o;
        return ss;
    }

    /**
     * 通过key获取map中的List<FileInfoModel>
     * @param map
     * @param key
     * @return
     */
    private List<FileInfoModel> getFiles(Map<String, Object> map,String key){
        Object o = map.get(key);
        if(o==null){
            return new ArrayList<>();
        }
        List<FileInfoModel> list = JSON.parseArray(JSONArray.toJSONString(o), FileInfoModel.class);
//        `
        return list;
    }

    /**
     * 根据文件分类数组  和 源数据map  填充生成好Adapter需要的List<FileListDataBean>
     * @param info
     * @param data
     * @return
     */
    private List<FileListDataBean> setListFile(String[] info, Map<String, Object> data){
        List<FileListDataBean> infolist = new ArrayList<>();
        for (String name:info) {
            FileListDataBean fileListDataBean = new FileListDataBean(name);
            List<FileInfoModel> files = getFiles(data, name);
            if(files!=null&& files.size()>0){
                fileListDataBean.addFilePath(files);
            }
            infolist.add(fileListDataBean);
        }
        return infolist;
    }
    private void initData() {
         id = getIntent().getIntExtra("id", -1);
        if(id == -1){
            showToast("未获取到项目详情");
            finish();
        }
    }

    private void initView() {
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        Resources res=getResources();
        baseinfo=res.getStringArray(R.array.baseinfo);
        repotrinfo=res.getStringArray(R.array.repotrinfo);
        buildinfo=res.getStringArray(R.array.buildinfo);
        fitmentinfo=res.getStringArray(R.array.fitmentinfo);
        otherinfo=res.getStringArray(R.array.otherinfo);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RestaurantMenuRightAdapter(mContext,new ArrayList<>());
        adapter.setmItemClickListener(new RestaurantMenuRightAdapter.OnItemClickListener() {
            @Override
            public void onChoiseClick(int pos) {
                UpLoadDataActivity.this.pos = pos;
                choiseWayDialog.show();
            }

            @Override
            public void onDeleteClick(String name, String dir) {
                deleteProjectFile(tvTypeName.getText().toString().trim()+"/"+dir,name);
                
            }
        });
        recycler.setAdapter(adapter);
        choiseWayDialog = new MyFillDialog(this,R.layout.dialog_choiseupway);
        choiseWayDialog.setCancelable(true);
        View tvPic = choiseWayDialog.findViewById(R.id.tvPic);
        View tvFile = choiseWayDialog.findViewById(R.id.tvFile);
        tvPic.setOnClickListener(dialogClick);
        tvFile.setOnClickListener(dialogClick);
    }
    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() ==R.id.tvFile ){
                // 选择文件
                choiseWayDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("data","file");
                openActivityWithResult(MineFileActivity.class,bundle,400);
            }  else if(v.getId() == R.id.tvPic){
                // 选择照片
                // 自由配置选项
                choiseWayDialog.dismiss();
                ISListConfig config = new ISListConfig.Builder()
                        // 是否多选, 默认true
                        .multiSelect(false)
                        // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                        .rememberSelected(false)
                        // “确定”按钮背景色
                        .btnBgColor(Color.GRAY)
                        // “确定”按钮文字颜色
                        .btnTextColor(Color.BLUE)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#4996FF"))
                        // 返回图标ResId
                        .backResId(R.mipmap.img_back)
                        // 标题
                        .title("图片")
                        // 标题文字颜色
                        .titleColor(Color.WHITE)
                        // TitleBar背景色
                        .titleBgColor(Color.parseColor("#4996FF"))
                        // 裁剪大小。needCrop为true的时候配置
                        .cropSize(1, 1, 200, 200)
                        .needCrop(false)
                        // 第一个是否显示相机，默认true
                        .needCamera(false)
                        // 最大选择图片数量，默认9
                        .maxNum(1)
                        .build();
                // 跳转到图片选择器
                ISNav.getInstance().toListActivity(mContext, config, 300);

            }
        }
    };
    @Override
    public void showData(Object data) {

    }

    @OnClick({R.id.rlChoiseType, R.id.tvNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlChoiseType:
                if(projectInfoBean!=null && checkUping()){
                    Bundle bundle = new Bundle();
                    if(projectInfoBean.getProject().getType()==2){
                       bundle.putString("data","small");
                    }
                    openActivityWithResult(ProgressChoiseActivity.class,bundle,200);
                }
                break;
            case R.id.tvNext:
                break;
        }
    }

    /**
     * 检查当前是否有文件在上传
     * @return
     */
    private boolean checkUping() {
        String name = tvTypeName.getText().toString().trim();
        List<FileListDataBean> list = new ArrayList<>();
        switch (name){
            case "装修项目基础资料":
                list = baseinfolists;
                break;
            case "图审上报资料":
                list = repotrinfolists;
                break;
            case "土建图纸":
                list = buildinfolists;
                break;
            case "装修图纸":
                list = fitmentinfolists;
                break;
            case "其他资料":
                list = otherinfolists;
                break;
        }
        for (int i = 0; i <list.size() ; i++) {
            List<FileInfoModel> filePath = list.get(i).getFilePath();
            if(filePath!=null){
                for (FileInfoModel f:filePath) {
                    if(f.isWaitingForUp()){
                        showToast("有文件正在上传，请等待上传完成");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private void deleteProjectFile(String dir,String name ){
        Map<String,String> map = new HashMap<>();
        map.put("projectId",id+"");
        map.put("dir", dir);
        map.put("name", name);
        MyModel.getNetData(MyModel.getRetrofitService().deleteFile(MyModel.getRequestHeaderMap("/upload/project/data"), map), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                showToast("删除文件成功");
                getData();
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if(pathList!=null && pathList.size()>0){
//               lists.get(pos).getFilePath().add(pathList.get(0));
               // 上传图片
//               adapter.setData(lists);
//                path = pathList.get(0);拿到图片 加入选择的地方
                //拿到图片的文件名
                File file = new File(pathList.get(0));
                String filename = file.getName();
                //拿到当前选中的文件夹档案
                String name = tvTypeName.getText().toString().trim();
                String dir = "";
                //1.根据当前的文件夹档案、点击选择文件位置pos 将未上传完成的图片放入adapter 2.获取点击位置的子文件夹名称
                switch (name){
                    case "装修项目基础资料":
                        dir = baseinfolists.get(pos).getItemName();
                        baseinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(baseinfolists);
                        break;
                    case "图审上报资料":
                        dir = repotrinfolists.get(pos).getItemName();
                        repotrinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(repotrinfolists);
                        break;
                    case "土建图纸":
                        dir = buildinfolists.get(pos).getItemName();
                        buildinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(buildinfolists);
                        break;
                    case "装修图纸":
                        dir = fitmentinfolists.get(pos).getItemName();
                        fitmentinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(fitmentinfolists);
                        break;
                    case "其他资料":
                        dir = otherinfolists.get(pos).getItemName();
                        otherinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(otherinfolists);
                        break;
                }
                //拼接出路径
                dir = name+File.separator+dir;
                upLoadFile(pathList.get(0),dir);


            }
        }else if(requestCode == 200 && resultCode == 200 && data!=null){
            String name = data.getStringExtra("name");
            tvTypeName.setText(name);
            switch (name){
                case "装修项目基础资料":
                    adapter.setData(baseinfolists);
                    break;
                case "图审上报资料":
                    adapter.setData(repotrinfolists);
                    break;
                case "土建图纸":
                    adapter.setData(buildinfolists);
                    break;
                case "装修图纸":
                    adapter.setData(fitmentinfolists);
                    break;
                case "其他资料":
                    adapter.setData(otherinfolists);
                    break;

            }
        }else if(requestCode == 400 && resultCode == 200 && data!=null){
            String filepath = data.getStringExtra("data");
            File file = new File(filepath);
            if(!file.exists()){
                return;
            }
            String filename = file.getName();
            //拿到当前选中的文件夹档案
            String name = tvTypeName.getText().toString().trim();
            String dir = "";
            //1.根据当前的文件夹档案、点击选择文件位置pos 将未上传完成的图片放入adapter 2.获取点击位置的子文件夹名称
            switch (name){
                case "装修项目基础资料":
                    dir = baseinfolists.get(pos).getItemName();
                    baseinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(baseinfolists);
                    break;
                case "图审上报资料":
                    dir = repotrinfolists.get(pos).getItemName();
                    repotrinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(repotrinfolists);
                    break;
                case "土建图纸":
                    dir = buildinfolists.get(pos).getItemName();
                    buildinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(buildinfolists);
                    break;
                case "装修图纸":
                    dir = fitmentinfolists.get(pos).getItemName();
                    fitmentinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(fitmentinfolists);
                    break;
                case "其他资料":
                    dir = otherinfolists.get(pos).getItemName();
                    otherinfolists.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(otherinfolists);
                    break;
            }
            //拼接出路径
            dir = name+File.separator+dir;
            upLoadFile(filepath,dir);

        }
    }



    /**
     * 文件上传
     * @param path 文件的path
     * @param dir  上传到的项目文件夹路径
     */
    private void upLoadFile(String path,String dir){
        File file = new File(path);
        String filename = file.getName();
        String nameSuffix = filename.substring(filename.lastIndexOf(".")+1);
        RequestBody requestFile = RequestBody.create(MediaType.parse(MediaTypeUtil.guessMimeTypeFromExtension(nameSuffix)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MyModel.getNetData(MyModel.getRetrofitService().uploadFile(MyModel.getRequestHeaderMap("/upload/project/data"), id, dir, body), new ICallBack<Map>() {
            @Override
            public void onSuccess(Map data) {
                //TODO 刷新上传文件对应的数据列表，判断当前显示的数据列表是否为此列表，刷新/不刷新
                getData();
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
