package com.ys.administrator.mydemo.model;

public class ProjectCreatBean extends BaseBean {

    /**
     * project : {"id":11,"type":1,"name":"富阳5层KTV","info":{"联系人":"xxx","电话":"xxx","功能":"xxx","建筑面积":"xxx","地址":"xxx"}}
     */

    private ProjectBean project;

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public static class ProjectBean {
        /**
         * id : 11
         * type : 1
         * name : 富阳5层KTV
         * info : {"联系人":"xxx","电话":"xxx","功能":"xxx","建筑面积":"xxx","地址":"xxx"}
         */

        private int id;
        private int type;
        private String name;
        private InfoBean info;

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

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * 联系人 : xxx
             * 电话 : xxx
             * 功能 : xxx
             * 建筑面积 : xxx
             * 地址 : xxx
             */

            private String 联系人;
            private String 电话;
            private String 功能;
            private String 建筑面积;
            private String 地址;

            public String get联系人() {
                return 联系人;
            }

            public void set联系人(String 联系人) {
                this.联系人 = 联系人;
            }

            public String get电话() {
                return 电话;
            }

            public void set电话(String 电话) {
                this.电话 = 电话;
            }

            public String get功能() {
                return 功能;
            }

            public void set功能(String 功能) {
                this.功能 = 功能;
            }

            public String get建筑面积() {
                return 建筑面积;
            }

            public void set建筑面积(String 建筑面积) {
                this.建筑面积 = 建筑面积;
            }

            public String get地址() {
                return 地址;
            }

            public void set地址(String 地址) {
                this.地址 = 地址;
            }
        }
    }
}
