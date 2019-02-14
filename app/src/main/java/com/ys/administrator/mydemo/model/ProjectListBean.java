package com.ys.administrator.mydemo.model;

import java.util.List;

public class ProjectListBean extends BaseBean {

    /**
     * count : 562
     * page : [{"id":10,"type":1,"name":"富阳5层KTV","status":1,"at":1546009861000},{"id":9,"type":1,"name":"富阳6层KTV","status":1,"at":1546009861000}]
     */

    private int count;
    private List<PageBean> page;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PageBean> getPage() {
        return page;
    }

    public void setPage(List<PageBean> page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * id : 10
         * type : 1
         * name : 富阳5层KTV
         * status : 1
         * at : 1546009861000
         */

        private int id;
        private int type;
        private String name;
        private int status;
        private long at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getAt() {
            return at;
        }

        public void setAt(long at) {
            this.at = at;
        }
    }
}
