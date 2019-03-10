package com.ys.administrator.mydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.model.City;

import java.util.List;



/**
 * Created by ${wj} on 2015/4/8 0008.
 */
public class CityAdapter extends BaseAdapter{

    private Context mContext;

    private List<City> list;

    /**
     * 构造函数
     * @return
     */
    public CityAdapter(Context mContext, List<City> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        City city=list.get(i);
        if(view==null)
        {
            holder=new ViewHolder();
            view=LayoutInflater.from(mContext).inflate(R.layout.item,null);
            holder.tvLetter= (TextView) view.findViewById(R.id.catalog);
            holder.tvTitle= (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section=getSectionForPosition(i);

        //如果当前位置等于该分类首字母的Char的位置，则认为是第一次出现
        if(i==getPositionForSection(section))
        {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(city.getSortLetters());
        }
        else
        {
            holder.tvLetter.setVisibility(View.GONE);
        }

        holder.tvTitle.setText(this.list.get(i).getCityName());

        return view;
    }

    final static class ViewHolder{
        TextView tvLetter;
        TextView tvTitle;
    }

    /**
     * 根据ListView的当前位置获取匪类的首字母的Char ascii值
     * @param position
     * @return
     */
    public int getSectionForPosition(int position){
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     * @param section
     * @return
     */
    public int getPositionForSection(int section){
        for(int i=0;i<getCount();i++){
            String sortStr=list.get(i).getSortLetters();
            char firstChar=sortStr.toUpperCase().charAt(0);
            if(firstChar==section)
            {
                return i;
            }
        }
        return -1;
    }
}
