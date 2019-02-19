package com.ys.administrator.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.adapter.RestaurantMenuRightAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.model.FileListDataBean;
import com.ys.administrator.mydemo.presenter.CommonPresenter;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class UpLoadDataActivity extends BaseActivity {

    @BindView(R.id.tvTypeName)
    TextView tvTypeName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    RestaurantMenuRightAdapter adapter;
    MyFillDialog choiseWayDialog;
    int pos;
    List<FileListDataBean> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_data);
        ButterKnife.bind(this);
        initToolbar("上传资料");
        initView();
        commonPresenter = new CommonPresenter();
        commonPresenter.attachView(this);
    }

    private void initView() {
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RestaurantMenuRightAdapter(mContext,new ArrayList<>());
        adapter.setmItemClickListener(new RestaurantMenuRightAdapter.OnItemClickListener() {
            @Override
            public void onChoiseClick(int pos) {
                UpLoadDataActivity.this.pos = pos;
                choiseWayDialog.show();
            }

            @Override
            public void onDeleteClick(int x, int y) {
                showToast("删除该文件");
            }
        });
        lists = new ArrayList<>();
        lists.add(new FileListDataBean("营业执照名称或预核名"));
        lists.add(new FileListDataBean("原始建筑图纸"));
        lists.add(new FileListDataBean("租赁合同"));
        lists.add(new FileListDataBean("建筑消防验收意见书"));
        recycler.setAdapter(adapter);
        adapter.setData(lists);
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
                //TODO 选择文件
            }  else if(v.getId() == R.id.tvPic){
                //TODO 选择照片
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
                break;
            case R.id.tvNext:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if(pathList!=null && pathList.size()>0){
               lists.get(pos).getFilePath().add(pathList.get(0));
               //TODO 上传图片
               adapter.setData(lists);
//                path = pathList.get(0);拿到图片 加入选择的地方

            }
        }
    }
}
