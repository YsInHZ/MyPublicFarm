package com.ys.administrator.mydemo.model;

import java.util.List;

public class MsgListBean extends BaseBean {

    private List<PageBean> page;

    public List<PageBean> getPage() {
        return page;
    }

    public void setPage(List<PageBean> page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * id : 10
         * title : 图审反馈
         * body : 请查阅附件
         * at : 1546009861000
         * read : true
         * files : [{"name":"xxx.doc","url":""}]
         */

        private int id;
        private String title;
        private String body;
        private long at;
        private boolean read;
        private List<FilesBean> files;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public long getAt() {
            return at;
        }

        public void setAt(long at) {
            this.at = at;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class FilesBean {
            /**
             * name : xxx.doc
             * url :
             */

            private String name;
            private String url;

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
}
