package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;

import java.io.File;
import java.util.List;

public class FileListAdapter extends BaseQuickAdapter<File,BaseViewHolder> {
    public FileListAdapter(int layoutResId, @Nullable List<File> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        helper.setText(R.id.tvName,item.getName());
    }
}
