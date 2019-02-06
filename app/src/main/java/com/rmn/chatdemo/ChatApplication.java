package com.rmn.chatdemo;

import android.app.Application;
import android.provider.Settings;

public class ChatApplication extends Application {

    public static ChatApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = new ChatApplication();
    }

}
