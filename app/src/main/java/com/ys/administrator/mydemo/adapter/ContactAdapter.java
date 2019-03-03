package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/21.
 */

public class ContactAdapter extends BaseQuickAdapter<Map<String,String>,BaseViewHolder> {
    public ContactAdapter(int layoutResId, @Nullable List<Map<String,String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String,String> item) {
        helper.setText(R.id.tvName,item.get("name")==null?"":item.get("name"));
        String type = item.get("type");
        if(type.equals("phone")){
            helper.setText(R.id.tvMsg,"点击直接拨打客服电话");
            helper.setImageResource(R.id.ciImg,R.mipmap.ing_phone);
        }else if(type.equals("wchat")){
            helper.setText(R.id.tvMsg,"长按复制微信客服账号");
            helper.setImageResource(R.id.ciImg,R.mipmap.img_wx);
        }else if(type.equals("qq")){
            helper.setText(R.id.tvMsg,"长按复制QQ客服账号");
            helper.setImageResource(R.id.ciImg,R.mipmap.img_qq);
        }

    }
}
