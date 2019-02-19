package com.ys.administrator.mydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.FileListDataBean;

import java.io.File;
import java.util.List;

public class RestaurantMenuRightAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<FileListDataBean> listBeans;
    int mItemCount;
    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    OnItemClickListener mItemClickListener;
    public RestaurantMenuRightAdapter(Context mContext, List<FileListDataBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
        getItemNum();
    }

    private void getItemNum(){
        mItemCount = 0;
        for (FileListDataBean lb:listBeans) {
            mItemCount += lb.getFilePath().size()+1;
        }
    }
    @Override
    public int getItemViewType(int position) {
        int sum=0;
        for(FileListDataBean menu:listBeans){
            if(position==sum){
                return MENU_TYPE;
            }
            sum+=menu.getFilePath().size()+1;
            if(position<sum){
                break;
            }
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==MENU_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fileup_filechoise, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fileup_file, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == MENU_TYPE){//当前是菜单类型
            MenuViewHolder menuholder = (MenuViewHolder)holder;
            menuholder.name.setText(getMenuByPosition(position).getItemName());
            menuholder.choise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onChoiseClick(getMenuIndexByPosition(position));
                }
            });
        }else {
            DishViewHolder dishholder = (DishViewHolder) holder;
            String item = getDishByPosition(position);
            File file = new File(item);
            ((DishViewHolder) holder).name.setText(file.getName());
            file=null;
            final int[] tresPosition = getTresPosition(position);
            ((DishViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onDeleteClick(tresPosition[0],tresPosition[1]);
                }
            });

        }
    }

    /**
     * 类别的viewHolder
     */
    private class MenuViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView choise;
        public MenuViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            choise = itemView.findViewById(R.id.tvChoise);
        }
    }
    /**
     * 文件的viewHolder
     */
    private class DishViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView delete;


        public DishViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            delete = itemView.findViewById(R.id.tvDelete);
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
    public FileListDataBean getMenuByPosition(int position){
        int sum =0;
        for(FileListDataBean menu:listBeans){
            if(position==sum){
                return menu;
            }
            sum+=menu.getFilePath().size()+1;
        }
        return null;
    }
    public int getMenuIndexByPosition(int position){
        int sum =0;
        for (int i = 0; i <listBeans.size() ; i++) {
            if(position==sum){
                return i;
            }
            sum+=listBeans.get(i).getFilePath().size()+1;
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
        for(FileListDataBean menu:listBeans){
            if(position>0 && position<=menu.getFilePath().size()){
                result[1] = position-1;
                return result;
            }
            else{
                result[0]++;
                position-=menu.getFilePath().size()+1;
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
            pos += listBeans.get(i).getFilePath().size()+1;
        }

       return pos;


    }
    /**
     * 根据位置获取菜品数据
     * @param position
     * @return
     */
    public String getDishByPosition(int position){
        for(FileListDataBean menu:listBeans){
            if(position>0 && position<=menu.getFilePath().size()){
                return menu.getFilePath().get(position-1);
            }
            else{
                position-=menu.getFilePath().size()+1;
            }
        }
        return null;
    }
    public void setData(List<FileListDataBean> listBeans){
        this.listBeans = listBeans;
        getItemNum();
        notifyDataSetChanged();
    }
    public static interface OnItemClickListener {
        void onChoiseClick(int pos);
        void onDeleteClick(int x, int y);
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
