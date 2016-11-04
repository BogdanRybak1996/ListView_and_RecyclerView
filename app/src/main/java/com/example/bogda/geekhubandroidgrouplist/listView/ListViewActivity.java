package com.example.bogda.geekhubandroidgrouplist.listView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bogda.geekhubandroidgrouplist.R;

public class ListViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_list_view,new ListViewFragment()).commit();
        }
        getSupportActionBar().setTitle("Group list");
    }

}
