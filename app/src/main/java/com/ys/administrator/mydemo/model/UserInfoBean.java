package com.ys.administrator.mydemo.model;

public class UserInfoBean extends BaseBean {

    /**
     * user : {"id":1,"mobile":"130xxxx","loginToken":"1234567890abcdef","loginAt":1546009861000,"isAdmin":true}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 1
         * mobile : 130xxxx
         * loginToken : 1234567890abcdef
         * loginAt : 1546009861000
         * isAdmin : true
         */

        private int id;
        private String mobile;
        private String loginToken;
        private long loginAt;
        private long expireAt;
        private boolean isAdmin;

        public long getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(long expireAt) {
            this.expireAt = expireAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public long getLoginAt() {
            return loginAt;
        }

        public void setLoginAt(long loginAt) {
            this.loginAt = loginAt;
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }
    }
}
