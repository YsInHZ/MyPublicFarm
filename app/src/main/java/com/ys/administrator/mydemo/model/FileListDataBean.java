package com.ys.administrator.mydemo.model;

import java.util.ArrayList;
import java.util.List;

public class FileListDataBean {
    public FileListDataBean(String itemName) {
        this.itemName = itemName;
        filePath = new ArrayList<>();
    }

    private String itemName;
    private List<String> filePath;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }
}
