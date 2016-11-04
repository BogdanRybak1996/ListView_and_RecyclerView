package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.GooglePlusUser;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by bohdan on 04.11.16.
 */

public class GooglePlusUserInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_google_plus_user_info, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        String json = getActivity().getIntent().getStringExtra("json");
        Gson gson = new Gson();
        GooglePlusUser user = gson.fromJson(json,GooglePlusUser.class);
        user.getBirthday();

        //Cover photo
        if(user.getCover() != null) {
            ImageView coverImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_background);
            Picasso.with(getActivity()).load(user.getCover().getCoverPhoto().getUrl()).into(coverImageView);
        }

        //Main photo
        if(user.getImage() != null) {
            ImageView mainImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_main);
            String url = user.getImage().getUrl().replaceFirst("sz=50","sz=200");
            Picasso.with(getActivity()).load(url).into(mainImageView);
        }
    }
}