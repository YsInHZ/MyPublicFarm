package com.ys.administrator.mydemo.model;

import java.util.List;

public class DocBean extends BaseBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 建筑工具原型0221.zip
         * url : /template/建筑工具原型0221.zip
         */


        private String name;
        private String url;
        private boolean isDownLoad = false;
        private int progress = -1;

        public ListBean(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public ListBean() {
        }

        public boolean isDownLoad() {
            return isDownLoad;
        }

        public void setDownLoad(boolean downLoad) {
            isDownLoad = downLoad;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
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
}
