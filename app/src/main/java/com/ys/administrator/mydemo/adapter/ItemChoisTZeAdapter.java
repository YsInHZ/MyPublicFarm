package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.StatusListBean;

import java.util.List;

public class ItemChoisTZeAdapter extends BaseQuickAdapter<StatusListBean.ListBean.TypesBean, BaseViewHolder> {
    public ItemChoisTZeAdapter(int layoutResId, @Nullable List<StatusListBean.ListBean.TypesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusListBean.ListBean.TypesBean item) {
        helper.setText(R.id.tvName,item.getName()+"");
    }
}
