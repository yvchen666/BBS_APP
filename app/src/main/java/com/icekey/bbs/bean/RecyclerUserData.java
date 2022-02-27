package com.icekey.bbs.bean;

public class RecyclerUserData {
    public String ico;
    public String userName;
    public boolean isCheck;

    public String getIco() {
        return ico;
    }

    public RecyclerUserData setIco(String ico) {
        this.ico = ico;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RecyclerUserData setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public RecyclerUserData setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public RecyclerUserData(String ico, String userName, boolean isCheck) {
        this.ico = ico;
        this.userName = userName;
        this.isCheck = isCheck;
    }
}
