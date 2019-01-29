package com.ys.administrator.mydemo.model;

public class UserInfoDetialBean extends BaseBean {

    /**
     * user : {"mobile":"17326036616","nickname":null,"id":3,"avatar":null,"at":1548316819569,"gender":0}
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
         * mobile : 17326036616
         * nickname : null
         * id : 3
         * avatar : null
         * at : 1548316819569
         * gender : 0
         */

        private String mobile;
        private String nickname;
        private int id;
        private String avatar;
        private long at;
        private int gender;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getAt() {
            return at;
        }

        public void setAt(long at) {
            this.at = at;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }
}
