package com.ys.administrator.mydemo.model;

import java.util.ArrayList;
import java.util.List;

public class FileLocalListBean {
    public String titleName;
    public List<LocalFile> localFiles = new ArrayList<>();

    public FileLocalListBean() {
    }

    public FileLocalListBean(String titleName) {
        this.titleName = titleName;
    }

    public void addFileLocalListBean(String name, String url, String s) {
        localFiles.add(new LocalFile(name,url,s));
    }

    public static class LocalFile extends FileInfoModel{
        private String localPath;

        public LocalFile(String name, String url, String localPath) {
            super(name, url);
            this.localPath = localPath;
        }

        public LocalFile(String name, boolean waitingForUp, String localPath) {
            super(name, waitingForUp);
            this.localPath = localPath;
        }

        public LocalFile(String localPath) {
            this.localPath = localPath;
        }
        public LocalFile( ) {
            this.localPath = localPath;
        }

        public String getLocalPath() {
            return localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }
    }
}
