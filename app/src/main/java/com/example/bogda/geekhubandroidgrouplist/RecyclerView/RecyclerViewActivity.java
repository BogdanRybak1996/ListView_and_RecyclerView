package com.example.bogda.geekhubandroidgrouplist.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_recycle_view,new RecyclerViewFragment()).commit();
        }
        getSupportActionBar().setTitle("Group list");
    }

}

