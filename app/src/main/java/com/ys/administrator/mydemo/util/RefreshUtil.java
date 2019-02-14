package com.ys.administrator.mydemo.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.ys.administrator.mydemo.R;

/**
 * Created by Administrator on 2018/6/14.
 * TwinklingRefreshLayout 设置工具类
 */

public class RefreshUtil {
    /**
     * 设置刷新头
     * @param context
     * @param refresh
     */
    public static void setRefreshHead(Context context,TwinklingRefreshLayout refresh){
        SinaRefreshView headerView = new SinaRefreshView(context);
        headerView.setTextColor(Color.parseColor("#cccccc"));
        headerView.setArrowResource(R.mipmap.img_loudou);
        refresh.setHeaderView(headerView);
    }

    /**
     * 设置加载更多
     * @param context
     * @param refresh
     */
    public static void setLoadBottom(Context context,TwinklingRefreshLayout refresh){
        LoadingView loadingView = new LoadingView(context);
        refresh.setBottomView(loadingView);
    }

    /**
     * 设置Recyclerview 的滑动冲突
     * @param recycle
     * @param refreshLayout
     */
    public static void setRefreshEvent(RecyclerView recycle,TwinklingRefreshLayout refreshLayout){
        recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    if(((LinearLayoutManager) recycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition()==0){
                        refreshLayout.setEnableRefresh(true);
                    }else{
                        refreshLayout.setEnableRefresh(false);
                    }
                }
                return false;
            }
        });
    }
    public static void setRefreshAndLoadMoreEvent(RecyclerView recycle,TwinklingRefreshLayout refreshLayout){
        recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    if (recycle.computeVerticalScrollExtent() + recycle.computeVerticalScrollOffset()
                            >= recycle.computeVerticalScrollRange()){
                        refreshLayout.setEnableLoadmore(true);
                    }else {
                        refreshLayout.setEnableLoadmore(false);
                    }
                    if(((LinearLayoutManager) recycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition()==0){
                        refreshLayout.setEnableRefresh(true);
                    }else{
                        refreshLayout.setEnableRefresh(false);
                    }

                }
                return false;
            }
        });
    }
}
