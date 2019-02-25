package com.ys.administrator.mydemo.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.MsgListBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MsgListAdapter extends BaseQuickAdapter<MsgListBean.PageBean, BaseViewHolder> {
    public MsgListAdapter(int layoutResId, @Nullable List<MsgListBean.PageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgListBean.PageBean item) {
        helper.setText(R.id.tvName,item.getTitle());
        helper.setText(R.id.tvCon,item.getBody());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.getAt());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        helper.setText(R.id.tvTime,format.format(calendar.getTime()));
        View view = helper.getView(R.id.viewpoint);
        GradientDrawable background = (GradientDrawable)view.getBackground();
        if(item.isRead()){
            background.setColor(Color.parseColor("#DCDCDC"));
        }else {
            background.setColor(Color.parseColor("#FFC740"));
        }


        if(item.getFiles()!=null && item.getFiles().size()>0){
            helper.setVisible(R.id.ivImg,true);
        }else {
            helper.setVisible(R.id.ivImg,false);
        }
    }
}
