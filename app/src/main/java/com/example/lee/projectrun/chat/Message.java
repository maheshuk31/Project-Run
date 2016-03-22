package com.example.lee.projectrun.chat;

import com.example.lee.projectrun.UserInformation;

import java.io.Serializable;

public class Message implements Serializable {

    //Think it needs to be linked with user info from database but unsure if done correctly
    String id, message, createdAt;
    UserInformation user;

    public Message() {
    }

    public Message(String id, String message, String createdAt, UserInformation user) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserInformation getUser() {
        return user;
    }

    public void setUser(UserInformation user) {
        this.user = user;
    }
}
