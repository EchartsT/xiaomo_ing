package com.book.domain;

import java.io.InputStream;
import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String userName;
    private boolean isSubscribe;
    private String chatData;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }

    public String getChatData() {
        return chatData;
    }

    public void setChatData(String chatData) {
        this.chatData = chatData;
    }
}
