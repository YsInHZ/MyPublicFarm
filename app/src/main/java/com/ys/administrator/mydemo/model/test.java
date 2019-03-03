package com.ys.administrator.mydemo.model;

import java.util.List;

public class test {

    /**
     * code : 200
     * list : [{"id":1,"name":"图审","types":[{"id":1,"name":"土建"},{"id":3,"name":"装修"}]},{"id":2,"name":"小微","types":[{"id":2,"name":"土建"},{"id":4,"name":"装修"}]}]
     */

    private int code;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : 图审
         * types : [{"id":1,"name":"土建"},{"id":3,"name":"装修"}]
         */

        private int id;
        private String name;
        private List<TypesBean> types;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class TypesBean {
            /**
             * id : 1
             * name : 土建
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
