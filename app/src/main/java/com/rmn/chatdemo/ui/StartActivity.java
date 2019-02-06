package com.rmn.chatdemo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rmn.chatdemo.PrefManager;
import com.rmn.chatdemo.R;
import com.rmn.chatdemo.Repository;

public class StartActivity extends Activity {

    EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        nameView = findViewById(R.id.name);

        // Store
        PrefManager.setUserId(this, Settings
                .Secure
                .getString(  this.getContentResolver(), Settings.Secure.ANDROID_ID));

        if (PrefManager.getUserName(this) != null){
            Intent intent = new Intent(StartActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void addUser(View view) {
        if (!TextUtils.isEmpty(nameView.getText())) {

            // add new user or overwrite already existing user to firebase database
            // if user user successfully added then screen will move to the chat screen
            Repository.addUser(this, nameView.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(StartActivity.this, ChatActivity.class);
                            PrefManager.setUserName(StartActivity.this, nameView.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
    }
}
