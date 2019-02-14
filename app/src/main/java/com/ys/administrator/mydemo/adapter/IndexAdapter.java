package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.ProjectListBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/19.
 */

public class IndexAdapter extends BaseQuickAdapter<ProjectListBean.PageBean,BaseViewHolder> {
    public IndexAdapter(int layoutResId, @Nullable List<ProjectListBean.PageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.PageBean item) {
        helper.setText(R.id.tvName,item.getName());
    }
}
