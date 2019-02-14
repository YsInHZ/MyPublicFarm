package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.StatusListBean;

import java.util.List;

public class ItemChoiseAdapter extends BaseQuickAdapter<StatusListBean.ListBean, BaseViewHolder> {
    public ItemChoiseAdapter(int layoutResId, @Nullable List<StatusListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusListBean.ListBean item) {
        helper.setText(R.id.tvName,item.getName()+"");
    }
}
