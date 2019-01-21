package com.ys.administrator.mydemo.model;

import java.util.List;

public class CaipiaoBean extends BaseBean{

    /**
     * reason : 查询成功
     * result : [{"lottery_id":"ssq","lottery_name":"双色球","lottery_type_id":"1","remarks":"每周二、四、日开奖"},{"lottery_id":"dlt","lottery_name":"超级大乐透","lottery_type_id":"2","remarks":"每周一、三、六开奖"},{"lottery_id":"qlc","lottery_name":"七乐彩","lottery_type_id":"1","remarks":"每周一、三、五开奖"},{"lottery_id":"fcsd","lottery_name":"福彩3D","lottery_type_id":"1","remarks":"每日开奖"},{"lottery_id":"qxc","lottery_name":"七星彩","lottery_type_id":"2","remarks":"每周二、五、日开奖"},{"lottery_id":"pls","lottery_name":"排列3","lottery_type_id":"2","remarks":"每日开奖"},{"lottery_id":"plw","lottery_name":"排列5","lottery_type_id":"2","remarks":"每日开奖"}]
     * error_code : 0
     */


    private List<ResultBean> result;


    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lottery_id : ssq
         * lottery_name : 双色球
         * lottery_type_id : 1
         * remarks : 每周二、四、日开奖
         */

        private String lottery_id;
        private String lottery_name;
        private String lottery_type_id;
        private String remarks;

        public String getLottery_id() {
            return lottery_id;
        }

        public void setLottery_id(String lottery_id) {
            this.lottery_id = lottery_id;
        }

        public String getLottery_name() {
            return lottery_name;
        }

        public void setLottery_name(String lottery_name) {
            this.lottery_name = lottery_name;
        }

        public String getLottery_type_id() {
            return lottery_type_id;
        }

        public void setLottery_type_id(String lottery_type_id) {
            this.lottery_type_id = lottery_type_id;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
