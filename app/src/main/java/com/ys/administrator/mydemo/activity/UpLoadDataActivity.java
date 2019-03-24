package com.ys.administrator.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.MediaTypeUtil;
import com.ys.administrator.mydemo.util.SortUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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
    MyFillDialog reNameDialog;
    MyFillDialog imgDialog;
    int pos;
//    List<FileListDataBean> baseinfolists,repotrinfolists,buildinfolists,fitmentinfolists,otherinfolists;
//    String[] baseinfo,repotrinfo,buildinfo,fitmentinfo,otherinfo;
    //存储解析后的所有数据集
    Map<String,List<FileListDataBean>> mapLists;
    //存储解析后的所有文件名集
    List<String> keyLists;
    int id;
    ProjectInfoBean projectInfoBean;
    Disposable disposable;
    EditText etName;
    String renameDir,renameOldName;
    ImageView img;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(checkUping()){
            setResult(200);
            finish();
        }

        return false;
    }
    /**
     * 获取项目详情数据
     */
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
            public void onError() { }

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
        mapLists = new HashMap<>();
        keyLists = new ArrayList<>();
        Map<String, Object> data = projectInfoBean.getProject().getData();
//        data.get("工程基本信息");
        Set<String> strings = data.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if("图审合格资料".equals(key)){
                continue;
            }
            Object o = data.get(key);
            if(o==null){
                continue;
            }
            final  String kestore = key;
            Map<String, Object> ss = (Map<String, Object>) o;
            List<FileListDataBean> fileListDataBeans = setListFile(ss);
            Collections.sort(fileListDataBeans, new Comparator<FileListDataBean>() {
                @Override
                public int compare(FileListDataBean o1, FileListDataBean o2) {
                    String itemName1 = o1.getItemName();
                    String itemName2 = o2.getItemName();
                    return SortUtil.sort(kestore,itemName1,itemName2);
                }
            });

            mapLists.put(key,fileListDataBeans);
            keyLists.add(key);
        }
//        data.get("工程基本信息");
//        Map<String, Object> zxxm = getMapObject(data, "装修项目基础资料");
//        Map<String, Object> tssb = getMapObject(data, "图审合格资料");
//        Map<String, Object> tjtz = getMapObject(data, "工程基本信息");
//        Map<String, Object> zxtz = getMapObject(data, "装修图纸");
//        Map<String, Object> qtzl = getMapObject(data, "其他资料");

//        baseinfolists = setListFile(zxxm);
//        repotrinfolists = setListFile(tssb);
//        buildinfolists = setListFile(tjtz);
//        fitmentinfolists = setListFile(zxtz);
//        otherinfolists = setListFile(qtzl);
        if(tvTypeName.getText().toString().trim().isEmpty()){
            for (int i = 0; i < keyLists.size(); i++) {
                if(keyLists.get(i).indexOf("基础资料")>-1){
                    tvTypeName.setText(keyLists.get(i));
                    break;
                }
            }
        }
        for (int i = 0; i < keyLists.size(); i++) {
            if(tvTypeName.getText().toString().trim().equals(keyLists.get(i))){
                adapter.setData(mapLists.get(keyLists.get(i)));
                break;
            }
        }


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
     * @param
     * @param data
     * @return
     */
    private List<FileListDataBean> setListFile( Map<String, Object> data){
        List<FileListDataBean> infolist = new ArrayList<>();
        Set<String> strings = data.keySet();
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            if("remark".equals(name))
                continue;
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
//        baseinfo=res.getStringArray(R.array.baseinfo);
//        repotrinfo=res.getStringArray(R.array.repotrinfo);
//        buildinfo=res.getStringArray(R.array.buildinfo);
//        fitmentinfo=res.getStringArray(R.array.fitmentinfo);
//        otherinfo=res.getStringArray(R.array.otherinfo);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RestaurantMenuRightAdapter(mContext,new ArrayList<>());
        adapter.setmItemClickListener(new RestaurantMenuRightAdapter.OnItemClickListener() {
            @Override
            public void onChoiseClick(int pos) {
                if(checkUping()){
                    UpLoadDataActivity.this.pos = pos;
                    choiseWayDialog.show();
                }

            }

            @Override
            public void onItemClick(String url) {
                if(!TextUtils.isEmpty(url)){
                    if(url.endsWith(".jpg") || url.endsWith(".png")){
                        Log.d(TAG, "onItemClick: "+url);
                        String s = Constant.BitmapBaseUrl + url + MyModel.getBitmapSign(url);
                        Log.d(TAG, "onItemClick: "+s);
                        Glide.with(mContext).load(s).into(img);
                        imgDialog.show();
                    }
                }
            }

            @Override
            public void onDeleteClick(String name, String dir) {
                deleteProjectFile(tvTypeName.getText().toString().trim()+"/"+dir,name);
                
            }

            @Override
            public void onRenameClick(String itemName, String dir) {
                // 重命名
                renameDir = tvTypeName.getText().toString().trim()+"/"+dir;
                renameOldName = itemName;
                etName.setText(itemName);
                reNameDialog.show();
            }

            @Override
            public void onCancelClick() {
                if(disposable!=null && !disposable.isDisposed()){
                    disposable.dispose();
                    getData();
                }
            }
        });
        recycler.setAdapter(adapter);
        choiseWayDialog = new MyFillDialog(this,R.layout.dialog_choiseupway);
        choiseWayDialog.setCancelable(true);
        View tvPic = choiseWayDialog.findViewById(R.id.tvPic);
        View tvFile = choiseWayDialog.findViewById(R.id.tvFile);
        tvPic.setOnClickListener(dialogClick);
        tvFile.setOnClickListener(dialogClick);

        reNameDialog = new MyFillDialog(this,R.layout.dialog_rename);
        reNameDialog.setCancelable(true);
         etName = (EditText) reNameDialog.findViewById(R.id.etName);
        View tvSure = reNameDialog.findViewById(R.id.tvSure);
        View tvCancel = reNameDialog.findViewById(R.id.tvCancel);
        tvSure.setOnClickListener(renameClick);
        tvCancel.setOnClickListener(renameClick);

        imgDialog= new MyFillDialog(this,R.layout.dialog_photoshow);
        imgDialog.setCancelable(true);
        img = (ImageView) imgDialog.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDialog.dismiss();
            }
        });
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
    View.OnClickListener renameClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tvCancel:
                    reNameDialog.dismiss();
                    break;
                case R.id.tvSure:
                    // 上传更名请求
                    String trim = etName.getText().toString().trim();
                    if(trim.isEmpty()){
                        showToast("文件名不能为空");
                        return;
                    }
                    if(trim.indexOf(".")==-1){
                        showToast("文件后缀不能为空");
                        return;
                    }
                    if(trim.lastIndexOf(".")==trim.length()-1){
                        showToast("文件后缀不能为空");
                        return;
                    }
                    if(trim.equals(renameOldName)){
                        showToast("文件名未变更");
                        return;
                    }
                    reNameDialog.dismiss();
                    renameProjectFile(renameDir,renameOldName,trim);
                    break;
            }
        }
    };
    @Override
    public void showData(Object data) {

    }

    @OnClick({R.id.rlChoiseType, /*R.id.tvNext*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlChoiseType:
                if(keyLists==null || keyLists.size()==0){
                    showToast("数据加载中");
                    return;
                }
                if(projectInfoBean!=null && checkUping()){
                    Bundle bundle = new Bundle();
                    if(projectInfoBean.getProject().getType()==4){
                       bundle.putString("data","small");
                    }
                    Collections.sort(keyLists, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            if(o1.indexOf("基础资料")!=-1){
                                return -1;
                            }else if(o2.indexOf("基础资料")!=-1){
                                return 1;
                            }else if(o1.indexOf("设计图纸")!=-1){
                                return -1;
                            }else if(o2.indexOf("设计图纸")!=-1){
                                return 1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    bundle.putString("list",JSON.toJSONString(keyLists));
                    openActivityWithResult(ProgressChoiseActivity.class,bundle,200);
                }
                break;
//            case R.id.tvNext:
//                break;
        }
    }

    /**
     * 检查当前是否有文件在上传
     * @return
     */
    private boolean checkUping() {
        String name = tvTypeName.getText().toString().trim();
        List<FileListDataBean> list = new ArrayList<>();
        for (int i = 0; i < keyLists.size(); i++) {
            if(name.equals(keyLists.get(i))){
                list = mapLists.get(keyLists.get(i));
                break;
            }
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
        MyModel.getNetData(mContext,MyModel.getRetrofitService().deleteFile(MyModel.getRequestHeaderMap("/upload/project/data"), map), new ICallBack() {
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
    private void renameProjectFile(String dir,String name ,String newName){
        Map<String,String> map = new HashMap<>();
        map.put("projectId",id+"");
        map.put("dir", dir);
        map.put("name", name);
        map.put("newName", newName);
        MyModel.getNetData(mContext,MyModel.getRetrofitService().renameFile(MyModel.getRequestHeaderMap("/upload/project/data"), map), new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                showToast("修改文件成功");
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
                for (int i = 0; i < keyLists.size(); i++) {
                    if(name.equals(keyLists.get(i))){
                        List<FileListDataBean> fileListDataBeans = mapLists.get(keyLists.get(i));
                        dir = fileListDataBeans.get(pos).getItemName();
                        fileListDataBeans.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                        adapter.setData(fileListDataBeans);
                        break;
                    }
                }

                //拼接出路径
                dir = name+File.separator+dir;
                upLoadFile(pathList.get(0),dir);
            }
        }else if(requestCode == 200 && resultCode == 200 && data!=null){
            String name = data.getStringExtra("name");
            tvTypeName.setText(name);
            for (int i = 0; i < keyLists.size(); i++) {
                if(name.equals(keyLists.get(i))){
                    List<FileListDataBean> fileListDataBeans = mapLists.get(keyLists.get(i));
                    adapter.setData(fileListDataBeans);
                    break;
                }
            }

        }else if(requestCode == 400 && resultCode == 200 && data!=null){
            String filepath = data.getStringExtra("data");
            File file = new File(filepath);
            if(!file.exists()){
                return;
            }

            if(file.length()/(1024*1024)>200){
                showToast("文件大小超过200M，请联系管理员");
                return;
            }
            String filename = file.getName();
            //拿到当前选中的文件夹档案
            String name = tvTypeName.getText().toString().trim();
            String dir = "";
            //1.根据当前的文件夹档案、点击选择文件位置pos 将未上传完成的图片放入adapter 2.获取点击位置的子文件夹名称
            for (int i = 0; i < keyLists.size(); i++) {
                if(name.equals(keyLists.get(i))){
                    List<FileListDataBean> fileListDataBeans = mapLists.get(keyLists.get(i));
                    dir = fileListDataBeans.get(pos).getItemName();
                    fileListDataBeans.get(pos).getFilePath().add(new FileInfoModel(filename,true));
                    adapter.setData(fileListDataBeans);
                    break;
                }
            }
            //拼接出路径
            dir = name+File.separator+dir;
            upLoadFile(filepath,dir);

        }
    }

    @Override
    protected void whenActivityFinish() {
        super.whenActivityFinish();
        if(disposable!=null &&  !disposable.isDisposed()){
            disposable.dispose();
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
        MyModel.getNetData(mContext,MyModel.getRetrofitService().uploadFile(MyModel.getRequestHeaderMap("/upload/project/data"), id, dir, body), new ICallBack<Map>() {
            @Override
            public void onSuccess(Map data) {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                //无论上传成功与否都刷新数据
                getData();
            }
        });
    }
}
