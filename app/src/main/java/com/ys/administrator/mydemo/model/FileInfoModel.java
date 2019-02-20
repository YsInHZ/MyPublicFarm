package com.ys.administrator.mydemo.model;

public class FileInfoModel {

    public FileInfoModel() {
    }

    public FileInfoModel(boolean waitingForUp) {
        this.waitingForUp = waitingForUp;
    }

    public FileInfoModel(String name, boolean waitingForUp) {
        this.name = name;
        this.waitingForUp = waitingForUp;
    }

    /**
     * name : xxx.doc
     * url : http://...
     */

    private String name;
    private String url;
    private boolean waitingForUp;

    public boolean isWaitingForUp() {
        return waitingForUp;
    }

    public void setWaitingForUp(boolean waitingForUp) {
        this.waitingForUp = waitingForUp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
