package com.ys.administrator.mydemo.model;

/**
 * Created by ${ys} .
 */
public class BaseEntity {
    private String sortLetters;//显示数据拼音的首字母

    /**获取首字母*/
    public String getSortLetters() {
        return sortLetters;
    }
    /**设置首字母*/
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
