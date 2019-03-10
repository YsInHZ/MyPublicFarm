package com.ys.administrator.mydemo.util;



import com.ys.administrator.mydemo.model.BaseEntity;

import java.util.Comparator;

/**
 * Created by ${wj} on 2015/4/8 0008.
 * 排序
 */
public class PinyinComparator implements Comparator<BaseEntity>{
    @Override
    public int compare(BaseEntity province, BaseEntity province2) {
        if(province.getSortLetters().equals("@")
                ||province2.getSortLetters().equals("#"))
        {
            return -1;
        }
        else if(province.getSortLetters().equals("#")
                ||province2.getSortLetters().equals("@"))
        {
            return 1;
        }
        else
        {
            return province.getSortLetters().compareTo(province2.getSortLetters());
        }
    }
}
