package com.icekey.bbs.bean;

import com.google.gson.annotations.SerializedName;

public class PostDataBean {

    @SerializedName("id")
    private Integer id;
    @SerializedName("postUser")
    private String postUser;
    @SerializedName("postHeader")
    private String postHeader;
    @SerializedName("postContent")
    private String postContent;
    @SerializedName("postPic")
    private String postPic;
    @SerializedName("postVideo")
    private String postVideo;
    @SerializedName("postTime")
    private String postTime;
    @SerializedName("postPlate")
    private String postPlate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getPostHeader() {
        return postHeader;
    }

    public void setPostHeader(String postHeader) {
        this.postHeader = postHeader;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostPic() {
        return postPic;
    }

    public void setPostPic(String postPic) {
        this.postPic = postPic;
    }

    public String getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(String postVideo) {
        this.postVideo = postVideo;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostPlate() {
        return postPlate;
    }

    public void setPostPlate(String postPlate) {
        this.postPlate = postPlate;
    }
}
