package com.ys.administrator.mydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.activity.MsgActivity;
import com.ys.administrator.mydemo.activity.ProjectDetialActivity;
import com.ys.administrator.mydemo.activity.ProjectEditActivity;
import com.ys.administrator.mydemo.activity.UpLoadDataActivity;
import com.ys.administrator.mydemo.adapter.IndexAdapter;
import com.ys.administrator.mydemo.base.BaseActivity;
import com.ys.administrator.mydemo.base.ICallBack;
import com.ys.administrator.mydemo.base.MyModel;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;
import com.ys.administrator.mydemo.model.MsgListBean;
import com.ys.administrator.mydemo.model.ProjectListBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.util.RefreshUtil;
import com.ys.administrator.mydemo.util.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import top.defaults.view.PickerView;


public class IndexFragment extends Fragment {


    @BindView(R.id.vPoint)
    View vPoint;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.rvProject)
    RecyclerView rvProject;
    Unbinder unbinder;
    IndexAdapter adapter;
    MyFillDialog typeDialog, progressDialog;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    ProjectListBean projectListBean;

    PickerView typePicker;
    PickerView progressPicker;
    StatusListBean statuList;
    StatusListBean typeList;
    /**
     * 判断刷新还是加载更多
     */
    boolean isRefresh = true;
    int typeid = -1;
    int progressid = -1;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvProgress)
    TextView tvProgress;

    public IndexFragment() {
        // Required empty public constructor
    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_index, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        getStatusList();
        getTypeList();
        getProjectList(new HashMap<>());
        getMsgOnReadList();
        return view;
    }

    private void initView() {
        adapter = new IndexAdapter(R.layout.item_indexproject, new ArrayList<>());
        adapter.setOnItemEdit(id -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id",id);
            ((BaseActivity)getActivity()).openActivity(ProjectEditActivity.class,bundle);
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            ProjectListBean.PageBean ib = (ProjectListBean.PageBean) adapter.getItem(position);
            bundle.putInt("id",ib.getId());
            ((BaseActivity)getActivity()).openActivity(ProjectDetialActivity.class,bundle);
        });
        rvProject.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProject.setAdapter(adapter);

        typeDialog = new MyFillDialog(getContext(), R.layout.dialog_itemchoise);
        progressDialog = new MyFillDialog(getContext(), R.layout.dialog_itemchoise);

        typePicker = (PickerView) typeDialog.findViewById(R.id.rvDialog);
        progressPicker = (PickerView) progressDialog.findViewById(R.id.rvDialog);
        View tvReset = typeDialog.findViewById(R.id.tvReset);
        View tvCancel = typeDialog.findViewById(R.id.tvCancel);
        View tvSure = typeDialog.findViewById(R.id.tvSure);
        View tvResetprogress = progressDialog.findViewById(R.id.tvReset);
        View tvCancelprogress = progressDialog.findViewById(R.id.tvCancel);
        View tvSureprogress = progressDialog.findViewById(R.id.tvSure);
        tvReset.setOnClickListener(typelistener);
        tvCancel.setOnClickListener(typelistener);
        tvSure.setOnClickListener(typelistener);
        tvResetprogress.setOnClickListener(progresslistener);
        tvCancelprogress.setOnClickListener(progresslistener);
        tvSureprogress.setOnClickListener(progresslistener);

        RefreshUtil.setRefreshHead(getContext(), refreshLayout);
        RefreshUtil.setLoadBottom(getContext(), refreshLayout);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isRefresh = true;
                        // 加上状态类型两个筛选条件
                        Map<String, String> map = new HashMap<>();
                        if (progressid > -1) {
                            map.put("status", progressid + "");
                        }
                        if (typeid > -1) {
                            map.put("type", typeid + "");
                        }
                        isRefresh = true;
                        getProjectList(map);
                    }
                });
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (projectListBean == null || projectListBean.getPage() == null || projectListBean.getPage().size() == 0) {
                            return;
                        }
                        isRefresh = false;
                        // 加上状态类型两个筛选条件
                        Map<String, String> map = new HashMap<>();
                        if (progressid > -1) {
                            map.put("status", progressid + "");
                        }
                        if (typeid > -1) {
                            map.put("type", typeid + "");
                        }
                        map.put("lastId", projectListBean.getPage().get(projectListBean.getPage().size() - 1).getId() + "");
                        getProjectList(map);
                    }
                });

            }
        });
    }

    /**
     * 获取状态列表
     */
    private void getStatusList() {
        MyModel.getNetData(getContext(),MyModel.getRetrofitService().getStatusList(), new ICallBack<StatusListBean>() {
            @Override
            public void onSuccess(StatusListBean data) {
                SPUtil.saveStatusList(data);
                statuList = data;
                progressPicker.setItems(data.getList(), item -> {
                });
                adapter.setStateList(data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    private void getMsgOnReadList(){
        MyModel.getNetData(getContext(),MyModel.getRetrofitService().getMsg(MyModel.getRequestHeaderMap("/user/msg"), true), new ICallBack<MsgListBean>() {
            @Override
            public void onSuccess(MsgListBean data) {
                if(data.getPage()==null || data.getPage().size()==0){
                    vPoint.setVisibility(View.GONE);
                }else {
                    vPoint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
    /**
     * 获取类型列表
     */
    private void getTypeList() {
        MyModel.getNetData(getContext(),MyModel.getRetrofitService().getTypeList(), new ICallBack<StatusListBean>() {
            @Override
            public void onSuccess(StatusListBean data) {
                SPUtil.saveTypeList(data);
                typeList = data;
                typePicker.setItems(data.getList(), item -> {
                });
                adapter.setTypeList(data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 获取项目列表
     * 搜索项目
     */
    private void getProjectList(Map<String, String> map) {
        if (!etSearch.getText().toString().trim().isEmpty()) {
            map.put("name", etSearch.getText().toString().trim());
        }
        MyModel.getNetData(getContext(),MyModel.getRetrofitService().getProjectSearch(MyModel.getRequestHeaderMap("/project/search"), map), new ICallBack<ProjectListBean>() {
            @Override
            public void onSuccess(ProjectListBean data) {
                num.setText(data.getCount() + "");
                if (isRefresh) {
                    projectListBean = data;
                } else {
                    projectListBean.getPage().addAll(data.getPage());
                }
                adapter.setNewData(projectListBean.getPage());
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlMsg, R.id.ivSearch, R.id.llType, R.id.llPlan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlMsg:
                // 消息中心
                ((BaseActivity)getActivity()).openActivityWithResult(MsgActivity.class,null,555);
                break;
            case R.id.ivSearch:
                Map<String, String> map = new HashMap<>();
                if (progressid > -1) {
                    map.put("status", progressid + "");
                }
                if (typeid > -1) {
                    map.put("type", typeid + "");
                }
                isRefresh = true;
                getProjectList(map);
                break;
            case R.id.llType:
                typeDialog.show();
                break;
            case R.id.llPlan:
                progressDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== 555&& resultCode==200){
            getMsgOnReadList();
        }else if(requestCode == 110 && resultCode==200){
            isRefresh = true;
            getProjectList(new HashMap<>());
        }
    }

    /**
     * 类型dialog的bottom点击
     */
    View.OnClickListener typelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvReset:
                    typeid = -1;
                    typeDialog.dismiss();
                    Map<String, String> map = new HashMap<>();
                    if (progressid > -1) {
                        map.put("status", progressid + "");
                    }

                    isRefresh = true;
                    getProjectList(map);
                    tvType.setText("项目类型");
                    break;
                case R.id.tvCancel:
                    typeDialog.dismiss();
                    break;
                case R.id.tvSure:
                    StatusListBean.ListBean selectedItem = typePicker.getSelectedItem(StatusListBean.ListBean.class);
                    typeid = selectedItem.getId();
                    typeDialog.dismiss();
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("type", typeid + "");
                    if (progressid > -1) {
                        map1.put("status", progressid + "");
                    }
                    isRefresh = true;
                    getProjectList(map1);
                    tvType.setText(selectedItem.getName());
                    break;
            }
        }
    };
    /**
     * 进度dialog的bottom点击
     */
    View.OnClickListener progresslistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvReset:
                    progressid = -1;
                    progressDialog.dismiss();
                    Map<String, String> map = new HashMap<>();
                    if (typeid > -1) {
                        map.put("type", typeid + "");
                    }

                    isRefresh = true;
                    getProjectList(map);
                    tvProgress.setText("项目进度");

                    break;
                case R.id.tvCancel:
                    progressDialog.dismiss();
                    break;
                case R.id.tvSure:
                    StatusListBean.ListBean selectedItem = progressPicker.getSelectedItem(StatusListBean.ListBean.class);
                    progressid = selectedItem.getId();
                    progressDialog.dismiss();
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("status", progressid + "");
                    if (typeid > -1) {
                        map1.put("type", typeid + "");
                    }
                    isRefresh = true;
                    getProjectList(map1);
                    tvProgress.setText(selectedItem.getName());
                    break;
            }
        }
    };
}
