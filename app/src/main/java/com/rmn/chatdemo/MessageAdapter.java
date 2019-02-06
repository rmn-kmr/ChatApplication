package com.rmn.chatdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmn.chatdemo.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    Context context;
    List<Message> messageList;
    String myId;

    public MessageAdapter(Context context) {
        this.context = context;
        messageList = new ArrayList<>();
        myId =PrefManager.getUserId(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(context).inflate(i, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Message message = messageList.get(i);
        holder.msgView.setText(message.getMsg());
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);

        // if message sender id is matched with current user id in that case it send send msg  user view
        if (message.getSenderId().equals(myId)) {
            return R.layout.send_msg;
        } else
            return R.layout.receive_msg;
    }

    public void addMessageList(List<Message> messages) {
        messageList = messages;
        notifyDataSetChanged();
    }

    /*
    * addNewMessage(message)
    * @prams message holds reference of new message
    * it each time adds message data at zero index of ArrayList,
    * then notify Adapter that new message has been added at zero index.
    *
    * */
    public void addNewMessage(Message message) {
        messageList.add(0,message);
        notifyItemInserted(0);
    }

    public void clear(){
        messageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView msgView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            msgView = itemView.findViewById(R.id.msg);
        }
    }
}
