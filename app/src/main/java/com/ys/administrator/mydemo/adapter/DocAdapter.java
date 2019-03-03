package com.ys.administrator.mydemo.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.DocBean;
import com.ys.administrator.mydemo.util.FilePathUtil;

import java.io.File;
import java.util.List;

public class DocAdapter extends BaseQuickAdapter< DocBean.ListBean,BaseViewHolder> {
    btClick clickListener;

    public void setClickListener(btClick clickListener) {
        this.clickListener = clickListener;
    }

    public DocAdapter(int layoutResId, @Nullable List<DocBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DocBean.ListBean item) {
        helper.setText(R.id.tvName,item.getName());
        TextView view = helper.getView(R.id.tvDown);
        String name = item.getUrl();
        File mFile = new File(FilePathUtil.getFilePathWithOutEnd()+name);
        if(mFile.exists()){
            helper.setText(R.id.tvDown,"打开");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int [] pos = new int[]{helper.getLayoutPosition()};
                    clickListener.poenClick(item.getUrl(),pos);
                }
            });
        }else {
            helper.setText(R.id.tvDown,"下载");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int [] pos = new int[]{helper.getLayoutPosition()};
                    clickListener.downClick(item.getUrl(),pos);
                }
            });
        }
        if(item.isDownLoad() && item.getProgress()>0){
            helper.setVisible(R.id.tvDown,false);
            helper.setVisible(R.id.pbProgress,true);
            helper.setProgress(R.id.pbProgress,item.getProgress());
        }else {
            helper.setVisible(R.id.tvDown,true);
            helper.setVisible(R.id.pbProgress,false);
        }

    }
    public interface btClick{
        void downClick(String url,int [] pos);
        void poenClick(String url,int [] pos);
    }
}
