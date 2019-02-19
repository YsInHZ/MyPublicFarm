package com.ys.administrator.mydemo.model;

import java.util.List;

import top.defaults.view.PickerView;

public class StatusListBean extends BaseBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements  PickerView.PickerItem{
        public ListBean() {
        }

        public ListBean(String name) {
            this.name = name;
        }

        /**
         * id : 1
         * name : 开始设计
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

        @Override
        public String getText() {
            return name;
        }
    }
}
