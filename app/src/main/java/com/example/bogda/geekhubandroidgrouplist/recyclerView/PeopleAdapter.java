package com.example.bogda.geekhubandroidgrouplist.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.data.People;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.userInfoActivity.GitHubUserInfoActivity;

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

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyHolder> {
    ArrayList<People> peoples;
    Context context;
    private OnItemClickListener clickListener;

    public PeopleAdapter(Context context, ArrayList<People> peoples) {
        this.context = context;
        this.peoples = peoples;
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.nameTextView.setText(peoples.get(position).getName());
        final People people = peoples.get(position);
        holder.gitHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                HttpUrl url = HttpUrl.parse("https://api.github.com/users/" + people.getGitHubUserName());
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                if(isOnline(context)) {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(context, "Data get error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String jsonResult = response.body().string();
                            Intent intent = new Intent(context, GitHubUserInfoActivity.class);
                            intent.putExtra("json", jsonResult);
                            context.startActivity(intent);
                        }
                    });
                }
                else {
                    Toast.makeText(context,"Check internet connection",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public Button gitHubButton;

        public MyHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.item_name_textview);
            gitHubButton = (Button) itemView.findViewById(R.id.item_git_button);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public static boolean isOnline(Context ctx) {
        if (ctx == null)
            return false;

        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
