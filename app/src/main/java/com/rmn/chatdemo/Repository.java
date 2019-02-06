package com.rmn.chatdemo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rmn.chatdemo.model.Channel;
import com.rmn.chatdemo.model.Message;
import com.rmn.chatdemo.model.User;

public class Repository {

    private static FirebaseDatabase sFirebaseDatabase;
    private static DatabaseReference mDatabaseRef;

    // static method which Provides Firebase reference
    private static FirebaseDatabase getFirebaseInstance() {
        if (sFirebaseDatabase == null)
            sFirebaseDatabase = FirebaseDatabase.getInstance();
        return sFirebaseDatabase;
    }


    // static method which Provides Firebase Database reference
    public static DatabaseReference getDatabaseInstance() {
        if (mDatabaseRef == null) {
            mDatabaseRef = getFirebaseInstance().getReference();
        }
        return mDatabaseRef;
    }



    /*
     * addUser(Context context, String name)
     * @params context holds the ref of ContextWrapper
     * @params name holds the string value of user name
     *
     *  it implements code to send data to firebase database,
     *  at specific node.
     *
     *  endpoint of msg storage node is /users/
     *
     *  it returns Task which is useful add completion or failure listener
     *
     * */
    public static Task<Void> addUser(Context context, String name) {
        String userId = PrefManager.getUserId(context);
        User user = new User(userId, name);
        return getDatabaseInstance().child(Constants.Table.USER).child(userId).setValue(user);
    }



    /*
    * sendMessage(Context context, String msg)
    * @params context holds the ref of ContextWrapper
    * @params msg holds the string value of message
    *
    *  it implements code to send data to firebase database,
    *  at specific node.
    *
    *  endpoint of msg storage node is /chat_room/{channelId}
    *
    * */
    public static void sendMessage(Context context, String msg) {

        // message container reference
        Message message = new Message();

        // current selected channel reference
        Channel channel = PrefManager.getChannel(context);

        // ref is the reference node of chat room
        DatabaseReference ref = getDatabaseInstance().child(Constants.Table.CHAT_ROOM)
                .child(channel.getId());
        String key = ref.push().getKey();

        message.setId(key);
        message.setMsg(msg);
        message.setSenderId(PrefManager.getUserId(context));

        ref.child(key).setValue(message);
    }


}
