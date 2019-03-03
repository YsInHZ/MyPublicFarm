package com.ys.administrator.mydemo.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.ProjectFilesAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.http.DownLoadUtils;
import com.ys.administrator.mydemo.model.FileInfoModel;
import com.ys.administrator.mydemo.model.FileListDataBean;
import com.ys.administrator.mydemo.model.FileLocalListBean;
import com.ys.administrator.mydemo.model.ProjectInfoBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.util.FilePathUtil;
import com.ys.administrator.mydemo.util.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectDetialActivity extends BaseActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvC1)
    TextView tvC1;
    @BindView(R.id.tvC2)
    TextView tvC2;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.tvLxr)
    TextView tvLxr;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvGn)
    TextView tvGn;
    @BindView(R.id.tvJzmj)
    TextView tvJzmj;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    int id;
    List<FileListDataBean> baseinfolists,repotrinfolists,buildinfolists,fitmentinfolists,otherinfolists;
    //存储解析后的所有数据集
    Map<String,List<FileListDataBean>> mapLists;
    //存储解析后的所有文件名集
    List<String> keyLists;
//    String[] baseinfo,repotrinfo,buildinfo,fitmentinfo,otherinfo;
    ProjectFilesAdapter adapter;
    List<FileLocalListBean> fileList;
    ProjectInfoBean projectInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detial);
        ButterKnife.bind(this);
        initToolbar("项目详情");
        initData();
        initView();
        getData();
    }
    private void initData() {
        id = getIntent().getIntExtra("id", -1);
        if(id == -1){
            showToast("未获取到项目详情");
            finish();
        }
//        Resources res=getResources();
//        baseinfo=res.getStringArray(R.array.baseinfo);
//        repotrinfo=res.getStringArray(R.array.repotrinfo);
//        buildinfo=res.getStringArray(R.array.buildinfo);
//        fitmentinfo=res.getStringArray(R.array.fitmentinfo);
//        otherinfo=res.getStringArray(R.array.otherinfo);
    }
    private void initView() {
        tvEdit.setVisibility(View.INVISIBLE);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ProjectFilesAdapter(mContext,new ArrayList<>());
        recycler.setAdapter(adapter);
        adapter.setmItemClickListener(new ProjectFilesAdapter.OnItemClickListener() {
            @Override
            public void onDownClick(int x, int y) {
                int[] aa ={x,y};
                downLoadFile(fileList.get(x).localFiles.get(y).getUrl(),aa);
            }

            @Override
            public void onOpenClick(int x, int y) {
                String url = fileList.get(x).localFiles.get(y).getUrl();
                String s = FilePathUtil.getFilePathWithOutEnd() + url;
                showToast("请到\n"+s+"下查看文件");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(projectInfoBean==null)
            return false;
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",projectInfoBean.getProject().getName());
        bundle.putInt("typeid",projectInfoBean.getProject().getType());
        ProjectInfoBean.ProjectBean.InfoBean info = projectInfoBean.getProject().getInfo();
        bundle.putString("gn", info.get功能());
        bundle.putString("dz", info.get地址());
        bundle.putString("jzmj", info.get建筑面积());
        bundle.putString("dh", info.get电话());
        bundle.putString("lxr", info.get联系人());
        openActivityWithResult(ProjectEditActivity.class,bundle,111);
        return false;
    }
    /**
     * 获取项目详情数据
     */
    private void getData() {
        showUpingDialog();
        MyModel.getNetData(MyModel.getRetrofitService().getPeojectDetail(MyModel.getRequestHeaderMap("/project/info"), id), new ICallBack<ProjectInfoBean>() {
            @Override
            public void onSuccess(ProjectInfoBean data) {
                Log.d(TAG, "onSuccess: ");
                projectInfoBean = data;
                setDataInfo(data);
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
                finish();
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
     * @param projectInfoBean
     */
    private void setDataInfo(ProjectInfoBean projectInfoBean) {
        tvGn.setText(projectInfoBean.getProject().getInfo().get功能());
        tvLxr.setText(projectInfoBean.getProject().getInfo().get联系人());
        tvPhone.setText(projectInfoBean.getProject().getInfo().get电话());
        tvAddress.setText(projectInfoBean.getProject().getInfo().get地址());
        tvJzmj.setText(projectInfoBean.getProject().getInfo().get建筑面积());
        tvName.setText(projectInfoBean.getProject().getName());

        StatusListBean statusList = SPUtil.getStatusList();
        StatusListBean typeList = SPUtil.getTypeList();
        if(typeList!=null){
            for (StatusListBean.ListBean s:typeList.getList()) {
                if(s.getId()==projectInfoBean.getProject().getType()){
                    tvC2.setText(s.getName());
                }
            }
        }
        if(statusList!=null){
            for (StatusListBean.ListBean s:statusList.getList()) {
                if(s.getId()==projectInfoBean.getProject().getStatus()){
                    tvC1.setText(s.getName());
                }
            }
        }
        mapLists = new HashMap<>();
        keyLists = new ArrayList<>();
        Map<String, Object> data = projectInfoBean.getProject().getData();
//        data.get("工程基本信息");
        Set<String> strings = data.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Object o = data.get(key);
            if(o==null){
                continue;
            }
            Map<String, Object> ss = (Map<String, Object>) o;
            List<FileListDataBean> fileListDataBeans = setListFile(ss);
            mapLists.put(key,fileListDataBeans);
            keyLists.add(key);
        }
//        Map<String, Object> zxxm = getMapObject(data, "装修项目基础资料");
//        Map<String, Object> tssb = getMapObject(data, "图审合格资料");
//        Map<String, Object> tjtz = getMapObject(data, "工程基本信息");
//        Map<String, Object> zxtz = getMapObject(data, "装修图纸");
//        Map<String, Object> qtzl = getMapObject(data, "其他资料");
//
//        baseinfolists = setListFile(zxxm);
//        repotrinfolists = setListFile(tssb);
//        buildinfolists = setListFile(tjtz);
//        fitmentinfolists = setListFile(zxtz);
//        otherinfolists = setListFile(qtzl);
        // 设置数据到adapter
        fileList = new ArrayList<>();
        for (int i = 0; i < keyLists.size(); i++) {
            fileList.addAll(setAdapterList(mapLists.get(keyLists.get(i)),projectInfoBean.getProject().getName()+ "/"+keyLists.get(i)));
        }
//        fileList.addAll(setAdapterList(baseinfolists,projectInfoBean.getProject().getName()+ "/装修项目基础资料"));
//        fileList.addAll(setAdapterList(repotrinfolists,projectInfoBean.getProject().getName()+ "/图审合格资料"));
//        fileList.addAll(setAdapterList(buildinfolists,projectInfoBean.getProject().getName()+ "/工程基本信息"));
//        fileList.addAll(setAdapterList(fitmentinfolists,projectInfoBean.getProject().getName()+ "/装修图纸"));
//        fileList.addAll(setAdapterList(otherinfolists,projectInfoBean.getProject().getName()+ "/其他资料"));
        adapter.setData(fileList);

    }

    private List<FileLocalListBean> setAdapterList(List<FileListDataBean> baseinfolists, String Pathindex) {
        List<FileLocalListBean> fileList = new ArrayList<>();
        for (int i = 0; i < baseinfolists.size(); i++) {
            FileLocalListBean fileLocalListBean = new FileLocalListBean();
            String mindPath = Pathindex +"/"+ baseinfolists.get(i).getItemName();
            fileLocalListBean.titleName = mindPath;
            if( baseinfolists.get(i).getFilePath()!=null &&  baseinfolists.get(i).getFilePath().size()>0){
                for (int j = 0; j < baseinfolists.get(i).getFilePath().size(); j++) {
                    FileInfoModel fileInfoModel = baseinfolists.get(i).getFilePath().get(j);
                    fileLocalListBean.addFileLocalListBean(fileInfoModel.getName(), fileInfoModel.getUrl(),FilePathUtil.getFilePath()+mindPath+"/"+fileInfoModel.getName());
                }
                fileList.add(fileLocalListBean);
            }

        }
        return fileList;
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
    private List<FileInfoModel> getFiles(Map<String, Object> map, String key){
        Object o = map.get(key);
        if(o==null){
            return new ArrayList<>();
        }
        List<FileInfoModel> list = JSON.parseArray(JSONArray.toJSONString(o), FileInfoModel.class);
//        `
        return list;
    }

    /**
     * 根据源数据map  填充生成好Adapter需要的List<FileListDataBean>
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
            FileListDataBean fileListDataBean = new FileListDataBean(name);
            List<FileInfoModel> files = getFiles(data, name);
            if(files!=null&& files.size()>0){
                fileListDataBean.addFilePath(files);
            }
            infolist.add(fileListDataBean);
        }
        return infolist;
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
                    fileList.get(pos[0]).localFiles.get(pos[1]).setIsdownLoad(true);
                    fileList.get(pos[0]).localFiles.get(pos[1]).setDownloadLenth(currentLength);
                    adapter.notifyDataSetChanged();
                });

            }

            @Override
            public void onFinish(String localPath, int[] pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("文件已下载到"+localPath);
                        fileList.get(pos[0]).localFiles.get(pos[1]).setIsdownLoad(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==111 && resultCode == 200){
            getData();
        }
    }
}
