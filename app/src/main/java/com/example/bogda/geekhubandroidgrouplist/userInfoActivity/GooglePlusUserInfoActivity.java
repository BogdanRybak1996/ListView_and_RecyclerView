package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;

public class GooglePlusUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_plus_user_info);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_google_plus_user_info, new GooglePlusUserInfoFragment()).commit();
        }
        getSupportActionBar().setTitle("Google+ user info: " + getIntent().getStringExtra("name"));
    }
}
