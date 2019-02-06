package com.rmn.chatdemo.model;

public class Message {

    String id;
    String msg;
    String senderId;

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
