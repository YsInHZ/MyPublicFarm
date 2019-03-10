package com.ys.administrator.mydemo.model;

/**
 * Created by ${ys} .
 */
public class City extends BaseEntity{
    private int code;
    private String name;
    /**
     * 编码
     */
    private int parent_code;

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    public int getId() {
        return code;
    }

    public void setId(int id) {
        this.code = id;
    }

    public String getCityName() {
        return name;
    }

    public void setCityName(String cityName) {
        this.name = cityName;
    }

    public int getPrinvcecode() {
        return parent_code;
    }

    public void setPrinvcecode(int cityCode) {
        this.parent_code = cityCode;
    }
}
