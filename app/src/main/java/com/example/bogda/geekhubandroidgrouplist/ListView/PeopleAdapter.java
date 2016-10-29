package com.example.bogda.geekhubandroidgrouplist.ListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bogda.geekhubandroidgrouplist.People;
import com.example.bogda.geekhubandroidgrouplist.R;

import java.util.ArrayList;

/**
 * Created by bogda on 29.10.2016.
 */

public class PeopleAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<People> peoples;

    public PeopleAdapter(Context context, ArrayList<People> peoples) {
        this.context = context;
        this.peoples = peoples;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  peoples.size();
    }

    @Override
    public Object getItem(int position) {
        return peoples.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  convertView;
        if(view==null){                                                     //Check for recycling
            view = lInflater.inflate(R.layout.list_item,parent,false);
        }
        final People people = peoples.get(position);
        TextView nameTextView = (TextView) view.findViewById(R.id.item_name_textview);
        Button gitButton = (Button) view.findViewById(R.id.item_git_button);
        nameTextView.setText(people.getName());

        gitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,people.getGitHubLink());
                context.startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,people.getGoogleLink());
                context.startActivity(intent);
            }
        });
        return view;
    }


}
