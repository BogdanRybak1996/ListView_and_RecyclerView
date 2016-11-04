package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;

public class GitHubUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_user_info);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_git_hub_user_info,new GitHubUserInfoFragment()).commit();
        }
        getSupportActionBar().setTitle("GitHub user info: " + getIntent().getStringExtra("name"));
    }
}