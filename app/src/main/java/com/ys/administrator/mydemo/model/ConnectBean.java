package com.ys.administrator.mydemo.model;

public class ConnectBean extends BaseBean {

    /**
     * contact : {"qq":"","wechat":"","phone":"","at":1546009861000}
     */

    private ContactBean contact;

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public static class ContactBean {
        /**
         * qq :
         * wechat :
         * phone :
         * at : 1546009861000
         */

        private String qq;
        private String wechat;
        private String phone;
        private long at;

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getAt() {
            return at;
        }

        public void setAt(long at) {
            this.at = at;
        }
    }
}
