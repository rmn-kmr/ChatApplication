package com.rmn.chatdemo.model;

import com.rmn.chatdemo.Constants;

public class Channel {

    String id = Constants.DEFAULT_CHANNEL_ID;
    String channelName = Constants.DEFAULT_CHANNEL_NAME;

    public Channel(){
    }

    public Channel(String id, String channelName) {
        this.id = id;
        this.channelName = channelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
