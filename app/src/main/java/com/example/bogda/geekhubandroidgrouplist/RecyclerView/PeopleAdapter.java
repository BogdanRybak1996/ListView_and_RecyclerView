package com.example.bogda.geekhubandroidgrouplist.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.bogda.geekhubandroidgrouplist.Data.People;
import com.example.bogda.geekhubandroidgrouplist.R;

import java.util.ArrayList;

/**
 * Created by bogda on 29.10.2016.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyHolder> {
    ArrayList<People> peoples;
    Context context;
    private OnItemClickListener clickListener;

    public PeopleAdapter(Context context,ArrayList<People> peoples) {
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
        holder.gitHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: on git button click
            }
        });
    }
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public Button gitHubButton;
        public MyHolder(View itemView) {
            super(itemView);
            nameTextView  = (TextView) itemView.findViewById(R.id.item_name_textview);
            gitHubButton = (Button) itemView.findViewById(R.id.item_git_button);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

}
