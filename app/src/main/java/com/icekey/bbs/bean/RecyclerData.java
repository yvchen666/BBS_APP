package com.icekey.bbs.bean;

import java.util.Date;


public class RecyclerData {
    public static RecyclerData recyclerData;
    String ico_url;
    String userName;
    String date;
    String title;
    String content;
    String img_json;
    String text_read;
    String text_commit;
    String text_favorite;

    public static RecyclerData getRecyclerData() {
        return recyclerData;
    }

    public static void setRecyclerData(RecyclerData recyclerData) {
        RecyclerData.recyclerData = recyclerData;
    }

    public String getText_read() {
        return text_read;
    }

    public void setText_read(String text_read) {
        this.text_read = text_read;
    }

    public String getText_commit() {
        return text_commit;
    }

    public void setText_commit(String text_commit) {
        this.text_commit = text_commit;
    }

    public String getText_favorite() {
        return text_favorite;
    }

    public void setText_favorite(String text_favorite) {
        this.text_favorite = text_favorite;
    }


    public String getIco_url() {
        return ico_url;
    }

    public void setIco_url(String ico_url) {
        this.ico_url = ico_url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_json() {
        return img_json;
    }

    public void setImg_json(String img_json) {
        this.img_json = img_json;
    }

    private RecyclerData() {
    }

    public static class Builder {
        String ico_url = "http://139.155.90.20/bbs/ico.png";
        String userName = "Uname";
        String date = new Date().toString();
        String title = "NULL";
        String content = "test";
        String img_json = "['http://139.155.90.20/bbs/00.png','http://139.155.90.20/bbs/00.png','http://139.155.90.20/bbs/00.png']";
        String text_read = "0";
        String text_commit = "0";
        String text_favorite = "0";


        public Builder() {
        }

        public RecyclerData crate() {
            recyclerData = new RecyclerData();
            recyclerData.ico_url = ico_url;
            recyclerData.userName = userName;
            recyclerData.date = date;
            recyclerData.title = title;
            recyclerData.content = content;
            recyclerData.img_json = img_json;
            recyclerData.text_read = text_read;
            recyclerData.text_commit = text_commit;
            recyclerData.text_favorite = text_favorite;
            return recyclerData;
        }

        public Builder setIco_url(String ico_url) {
            this.ico_url = ico_url;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setImg_json(String img_json) {
            this.img_json = img_json;
            return this;
        }

        public Builder setText_read(String text_read) {
            this.text_read = text_read;
            return this;
        }

        public Builder setText_commit(String text_commit) {
            this.text_commit = text_commit;
            return this;
        }

        public Builder setText_favorite(String text_favorite) {
            this.text_favorite = text_favorite;
            return this;
        }
    }

}
