package com.example.bogda.geekhubandroidgrouplist.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_activity_photo, new PhotoFragment()).commit();
        }
        getSupportActionBar().setTitle("Make Photo");
    }
}
