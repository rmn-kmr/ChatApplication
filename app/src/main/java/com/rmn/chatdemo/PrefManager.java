package com.rmn.chatdemo;

import android.content.Context;
import android.content.SharedPreferences;

import com.rmn.chatdemo.model.Channel;

public class PrefManager {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferences getSharedPref(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        if (editor == null)
            editor = getSharedPref(context).edit();
        return editor;
    }

    public static void setUserName(Context context, String name) {
        getEditor(context).putString("username", name).apply();
    }

    public static String getUserName(Context context) {
        return getSharedPref(context).getString("username", null);
    }

    public static void setUserId(Context context, String id) {
        getEditor(context).putString("userid", id).apply();
    }

    public static String getUserId(Context context) {
        return getSharedPref(context).getString("userid", null);
    }

    public static void setChannel(Context context, Channel channel) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString("channelname", channel.getChannelName());
        editor.putString("channelId", channel.getId());
        editor.apply();
    }

    public static Channel getChannel(Context context) {
        return new Channel(getSharedPref(context).getString("channelId", Constants.DEFAULT_CHANNEL_ID),
                getSharedPref(context).getString("channelname", Constants.DEFAULT_CHANNEL_NAME));
    }
}
