package com.ys.administrator.mydemo.adapter;

import android.graphics.BitmapFactory;
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
        if(item.isDirectory()){
            helper.setImageResource(R.id.img,R.mipmap.img_filedirectory);
        }else {
            helper.setImageBitmap(R.id.img,BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.img_file));
        }
        helper.setText(R.id.tvName,item.getName());
    }
}
