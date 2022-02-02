package com.icekey.bbs.bean;

import com.google.gson.annotations.SerializedName;

public class LoginResponseBean {

    /**
     * {
     *     "msg": "success",
     *     "success": true,
     *     "code": "200",
     *     "detail": {
     *         "id": 1,
     *         "userName": "yvchen",
     *         "passWord": "123456",
     *         "qq_id": "1821383696",
     *         "wechat": "YVCHEN987",
     *         "user_icon": "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=433364479,2130205375&fm=111&gp=0.jpg",
     *         "phoneNumber": "17829054720",
     *         "creditScore": 0,
     *         "joinDate": null,
     *         "userLevel": "900",
     *         "integral": 99999,
     *         "state": 0,
     *         "vip": false
     *     }
     * }
     *
     * {
     *     "msg": "error",
     *     "success": false,
     *     "code": "403",
     *     "detail": null
     * }
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private String code;
    @SerializedName("detail")
    private DetailDTO detail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DetailDTO getDetail() {
        return detail;
    }

    public void setDetail(DetailDTO detail) {
        this.detail = detail;
    }

    public static class DetailDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("userName")
        private String userName;
        @SerializedName("passWord")
        private String passWord;
        @SerializedName("qq_id")
        private String qqId;
        @SerializedName("wechat")
        private String wechat;
        @SerializedName("user_icon")
        private String userIcon;
        @SerializedName("phoneNumber")
        private String phoneNumber;
        @SerializedName("creditScore")
        private Integer creditScore;
        @SerializedName("joinDate")
        private Object joinDate;
        @SerializedName("userLevel")
        private String userLevel;
        @SerializedName("integral")
        private Integer integral;
        @SerializedName("state")
        private Integer state;
        @SerializedName("vip")
        private Boolean vip;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getQqId() {
            return qqId;
        }

        public void setQqId(String qqId) {
            this.qqId = qqId;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Integer getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(Integer creditScore) {
            this.creditScore = creditScore;
        }

        public Object getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(Object joinDate) {
            this.joinDate = joinDate;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public Integer getIntegral() {
            return integral;
        }

        public void setIntegral(Integer integral) {
            this.integral = integral;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public Boolean getVip() {
            return vip;
        }

        public void setVip(Boolean vip) {
            this.vip = vip;
        }
    }
}
