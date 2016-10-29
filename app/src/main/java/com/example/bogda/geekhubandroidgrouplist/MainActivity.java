package com.example.bogda.geekhubandroidgrouplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bogda.geekhubandroidgrouplist.ListView.ListViewActivity;
import com.example.bogda.geekhubandroidgrouplist.RecyclerView.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button listViewButton = (Button) findViewById(R.id.list_view_button);
        Button recycleViewButton = (Button) findViewById(R.id.recycle_view_button);

        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListViewActivity.class);
                startActivity(intent);
            }
        });
        recycleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
