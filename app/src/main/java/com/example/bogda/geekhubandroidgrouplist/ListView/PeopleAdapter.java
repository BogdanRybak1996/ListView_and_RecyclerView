package com.example.bogda.geekhubandroidgrouplist.ListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.Data.GitHubUser;
import com.example.bogda.geekhubandroidgrouplist.Data.People;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                //dont't working
                OkHttpClient client = new OkHttpClient();
                HttpUrl url = HttpUrl.parse("https://api.github.com/users/" + people.getGitHubUserName());
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonResult = response.body().string();
                        Gson gson = new GsonBuilder().create();
                        GitHubUser currUser = gson.fromJson(jsonResult,GitHubUser.class);
                        //TODO: new Activity with currUser information
                    }
                });

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: item click
            }
        });
        return view;
    }


}
