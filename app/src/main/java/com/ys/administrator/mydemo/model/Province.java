package com.ys.administrator.mydemo.model;

/**
 * Created by ${ys} .
 */
public class Province extends BaseEntity{

    private int id;
    /**
     * 名称
     */
    private String provinceName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
