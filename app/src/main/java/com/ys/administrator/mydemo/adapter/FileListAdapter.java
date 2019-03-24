package com.ys.administrator.mydemo.adapter;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;

import java.io.File;
import java.util.List;

public class FileListAdapter extends BaseQuickAdapter<File,BaseViewHolder> {
    boolean isImage = false;
    public FileListAdapter(int layoutResId, @Nullable List<File> data) {
        super(layoutResId, data);
    }
    public FileListAdapter(int layoutResId, @Nullable List<File> data,boolean isImage) {
        super(layoutResId, data);
        this.isImage= isImage;
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        if(item.isDirectory()){
            helper.setImageResource(R.id.img,R.mipmap.img_filedirectory);
        }else {
            helper.setImageBitmap(R.id.img,BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.img_file));
        }
        if(isImage){
            ImageView view = (ImageView)helper.getView(R.id.img);
            Glide.with(mContext).load(Uri.fromFile(item)).into(view);
        }
        helper.setText(R.id.tvName,item.getName());
    }
}
