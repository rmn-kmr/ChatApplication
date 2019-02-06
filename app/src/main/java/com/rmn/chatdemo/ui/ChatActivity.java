package com.rmn.chatdemo.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.rmn.chatdemo.Constants;
import com.rmn.chatdemo.MessageAdapter;
import com.rmn.chatdemo.PrefManager;
import com.rmn.chatdemo.R;
import com.rmn.chatdemo.Repository;
import com.rmn.chatdemo.model.Channel;
import com.rmn.chatdemo.model.Message;

public class ChatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private EditText msgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize recyclerView and adapter
        // recyclerView = findViewById(R.id.recycle);
        // messageAdapter = new MessageAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(messageAdapter);

        msgView = findViewById(R.id.msgView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeChat(PrefManager.getChannel(this).getId());
        setTitle(PrefManager.getChannel(this).getChannelName());
        PrefManager.getSharedPref(this).registerOnSharedPreferenceChangeListener(this);

    }


    public void initializeChat(String channelId) {
        // clear message adapter message list
        messageAdapter.clear();

        Repository.getDatabaseInstance().child(Constants.Table.CHAT_ROOM).child(channelId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //get reference of new message from dataSnapshot
                Message message = dataSnapshot.getValue(Message.class);
                //add new message to messageAdapter
                messageAdapter.addNewMessage(message);
               // scroll recyclerView to the new message position
                recyclerView.scrollToPosition(0);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            setChannel(Constants.channel1);
         } else if (id == R.id.nav_gallery) {
            setChannel(Constants.channel2);
         } else if (id == R.id.nav_slideshow) {
            setChannel(Constants.channel3);
         } else if (id == R.id.nav_manage) {
            setChannel(Constants.channel4);
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setChannel(Channel channel) {
        // set new channel to sharedPreference
        PrefManager.setChannel(this, channel);
    }

    /*
    * sendMessage(View view) function will be called on click of Send button
    * to send message to the database
    * */
    public void sendMessage(View view) {

        // msgView is empty in that case it doesn't do anything simply return
        if (TextUtils.isEmpty(msgView.getText())) {
            return;
        }

        //sendMessage actually implements the code to send message to firebase database
        Repository.sendMessage(this, msgView.getText().toString());

        //after send msg msg View will be clear
        msgView.setText("");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // whenever user switch channel then we reset massage data list of adapter,
        // initialize database reference to the corresponding channel id.
        if (key.equals("channelId")){
            initializeChat(PrefManager.getChannel(ChatActivity.this).getId());
            setTitle(PrefManager.getChannel(this).getChannelName());
            msgView.setText("");
        }
    }
}
