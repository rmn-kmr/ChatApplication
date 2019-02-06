package com.rmn.chatdemo;

import com.rmn.chatdemo.model.Channel;

public class Constants {

    // Default value current channel id
    public static final String DEFAULT_CHANNEL_ID = "1001";

    // Default value current channel name
    public static final String DEFAULT_CHANNEL_NAME = "Channel 1";

    public static final Channel channel1 = new Channel("1001", "Channel 1");
    public static final Channel channel2 = new Channel("1002", "Channel 2");
    public static final Channel channel3 = new Channel("1003", "Channel 3");
    public static final Channel channel4 = new Channel("1004", "Channel 4");

    //Firebase database tables name
    public static class Table{
        public final static String USER = "users";
        public final static String CHAT_ROOM = "chat_room";
        public final static String CHANNEL = "channels";
    }

}
