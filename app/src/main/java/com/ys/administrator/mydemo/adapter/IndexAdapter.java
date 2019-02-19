package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.ProjectListBean;
import com.ys.administrator.mydemo.model.StatusListBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/19.
 */

public class IndexAdapter extends BaseQuickAdapter<ProjectListBean.PageBean,BaseViewHolder> {
    StatusListBean stateList,typeList;
    OnItemEdit onItemEdit;
    public IndexAdapter(int layoutResId, @Nullable List<ProjectListBean.PageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.PageBean item) {
        helper.setText(R.id.tvName,item.getName());
        if(stateList!=null){
            for (StatusListBean.ListBean s:stateList.getList()) {
                if(s.getId()==item.getStatus()){
                    helper.setText(R.id.tvC1,s.getName());
                }
            }
        }
        if(typeList!=null){
            for (StatusListBean.ListBean s:typeList.getList()) {
                if(s.getId()==item.getType()){
                    helper.setText(R.id.tvC2,s.getName());
                }
            }
        }
        View view = helper.getView(R.id.tvEdit);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemEdit.Edit(item.getId());
            }
        });
    }
    public void setStateList(StatusListBean stateList){
        this.stateList = stateList;
        notifyDataSetChanged();
    }
    public void setTypeList(StatusListBean typeList){
        this.typeList = typeList;
        notifyDataSetChanged();
    }
    public interface OnItemEdit{
        void Edit(int id);
    }

    public void setOnItemEdit(OnItemEdit onItemEdit) {
        this.onItemEdit = onItemEdit;
    }
}
