package com.ys.administrator.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.FileInfoModel;
import com.ys.administrator.mydemo.model.FileListDataBean;
import com.ys.administrator.mydemo.model.FileLocalListBean;
import com.ys.administrator.mydemo.util.FilePathUtil;

import java.io.File;
import java.util.List;

public class ProjectFilesAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<FileLocalListBean> listBeans;
    int mItemCount;
    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    OnItemClickListener mItemClickListener;
    public ProjectFilesAdapter(Context mContext, List<FileLocalListBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
        getItemNum();
    }

    private void getItemNum(){
        mItemCount = 0;
        for (FileLocalListBean lb:listBeans) {
            mItemCount += lb.localFiles.size()+1;
        }
    }
    @Override
    public int getItemViewType(int position) {
        int sum=0;
        for(FileLocalListBean menu:listBeans){
            if(position==sum){
                return MENU_TYPE;
            }
            sum+=menu.localFiles.size()+1;
            if(position<sum){
                break;
            }
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==MENU_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_files_title, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == MENU_TYPE){//当前是菜单类型
            MenuViewHolder menuholder = (MenuViewHolder)holder;
            menuholder.tvTitle.setText(getMenuByPosition(position).titleName);
        }else {
            DishViewHolder dishholder = (DishViewHolder) holder;
            FileLocalListBean.LocalFile item = getDishByPosition(position);

            String name = item.getUrl();
            File mFile = new File(FilePathUtil.getFilePathWithOutEnd()+name);
            if(mFile.exists()){
                ((DishViewHolder) holder).tvDown.setText("打开");
            }else {
                ((DishViewHolder) holder).tvDown.setText("下载");
            }
            if(item.isIsdownLoad() &&item.getDownloadLenth()>=0 ){
                ((DishViewHolder) holder).tvDown.setVisibility(View.GONE);
                ((DishViewHolder) holder).pbProgress.setVisibility(View.VISIBLE);
                ((DishViewHolder) holder).pbProgress.setProgress(item.getDownloadLenth());
                ((DishViewHolder) holder).tvDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }else {
                ((DishViewHolder) holder).tvDown.setVisibility(View.VISIBLE);
                ((DishViewHolder) holder).pbProgress.setVisibility(View.GONE);
                final int[] tresPosition = getTresPosition(position);
                ((DishViewHolder) holder).tvDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = ((DishViewHolder) holder).tvDown.getText().toString().trim();
                        if("下载".equals(trim)){
                            mItemClickListener.onDownClick(tresPosition[0],tresPosition[1]);
                        }else {
                            mItemClickListener.onOpenClick(tresPosition[0],tresPosition[1]);
                        }

                    }
                });
            }
            ((DishViewHolder) holder).tvName.setText(item.getName());



        }
    }

    /**
     * 类别的viewHolder
     */
    private class MenuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;

        public MenuViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
    /**
     * 文件的viewHolder
     */
    private class DishViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvDown;
        private ProgressBar pbProgress;


        public DishViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDown = itemView.findViewById(R.id.tvDown);
            pbProgress = itemView.findViewById(R.id.pbProgress);
        }

    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
    /**
     * 根据位置获取菜单类型数据（此位置必须是分类）
     * @param position
     * @return
     */
    public FileLocalListBean getMenuByPosition(int position){
        int sum =0;
        for(FileLocalListBean menu:listBeans){
            if(position==sum){
                return menu;
            }
            sum+=menu.localFiles.size()+1;
        }
        return null;
    }
    public int getMenuIndexByPosition(int position){
        int sum =0;
        for (int i = 0; i <listBeans.size() ; i++) {
            if(position==sum){
                return i;
            }
            sum+=listBeans.get(i).localFiles.size()+1;
        }

        return 0;
    }

    /**
     * 根据菜单位置获取二维坐标
     * @param position
     * @return
     */
    public int[] getTresPosition(int position){
        int [] result = new int[2];
        result[0] = 0;
        result[1] = -1;
        for(FileLocalListBean menu:listBeans){
            if(position>0 && position<=menu.localFiles.size()){
                result[1] = position-1;
                return result;
            }
            else{
                result[0]++;
                position-=menu.localFiles.size()+1;
            }
        }

        return result;
    }
    public int getTypePosition(int position){
       int pos = 0;
       if(position==0){
           return 0;
       }
        for (int i = 0; i < position; i++) {
            pos += listBeans.get(i).localFiles.size()+1;
        }

       return pos;


    }
    /**
     * 根据位置获取菜品数据
     * @param position
     * @return
     */
    public FileLocalListBean.LocalFile getDishByPosition(int position){
        for(FileLocalListBean menu:listBeans){
            if(position>0 && position<=menu.localFiles.size()){
                return menu.localFiles.get(position-1);
            }
            else{
                position-=menu.localFiles.size()+1;
            }
        }
        return null;
    }
    public void setData(List<FileLocalListBean> listBeans){
        this.listBeans = listBeans;
        getItemNum();
        notifyDataSetChanged();
    }
    public static interface OnItemClickListener {
        void onDownClick(int x, int y);
        void onOpenClick(int x, int y);
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
