package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.GooglePlusUser;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.Organization;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.Place;
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
        GooglePlusUser user = gson.fromJson(json, GooglePlusUser.class);
        user.getBirthday();

        //Cover photo
        if (user.getCover() != null) {
            ImageView coverImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_background);
            Picasso.with(getActivity()).load(user.getCover().getCoverPhoto().getUrl()).into(coverImageView);
        }

        //Main photo
        if (user.getImage() != null) {
            ImageView mainImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_main);
            String url = user.getImage().getUrl().replaceFirst("sz=50", "sz=200");
            Picasso.with(getActivity()).load(url).into(mainImageView);
        }

        //Name
        TextView nameTextView = (TextView) getActivity().findViewById(R.id.google_plus_user_info_name);
        nameTextView.setText(user.getDisplayName());

        //Click on google+ logo
        ImageView googleLogoImageView = (ImageView) getActivity().findViewById(R.id.google_plus_logo_image);
        final Uri url = Uri.parse(user.getUrl());
        googleLogoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(url);
                startActivity(intent);
            }
        });

        //Birthday
        TextView birthdayTextViewLabel = (TextView) getActivity().findViewById(R.id.google_plus_user_info_birthday_label);
        TextView birthdayTextView = (TextView) getActivity().findViewById(R.id.google_plus_user_info_birthday);
        if (user.getBirthday() != null) {
            birthdayTextView.setText(user.getBirthday());
        } else {
            birthdayTextViewLabel.setVisibility(View.GONE);
            birthdayTextView.setVisibility(View.GONE);
        }

        //Gender
        TextView genderTextViewLabel = (TextView) getActivity().findViewById(R.id.google_plus_user_info_gender_label);
        TextView genderTextView = (TextView) getActivity().findViewById(R.id.google_plus_user_info_gender);
        if (user.getGender() != null) {
            genderTextView.setText(user.getGender());
        } else {
            genderTextViewLabel.setVisibility(View.GONE);
            genderTextView.setVisibility(View.GONE);
        }

        //Organizations
        TextView organizationsTextViewLabel = (TextView) getActivity().findViewById(R.id.google_plus_user_info_organizations_label);
        LinearLayout organizationsLayout = (LinearLayout) getActivity().findViewById(R.id.google_plus_organizations_layout);
        organizationsLayout.removeAllViews();
        if (user.getOrganizations() != null) {
            int counter = 1;
            for (Organization o : user.getOrganizations()) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(18);
                textView.setText(Integer.toString(counter) + ") " + o.getName());
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                textView.setPaintFlags(p.getColor());
                textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                counter++;
                final Organization tempOrganization = o;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + tempOrganization.getName()));
                        startActivity(intent);
                    }
                });
                organizationsLayout.addView(textView);
            }
        } else {
            organizationsLayout.setVisibility(View.GONE);
            organizationsTextViewLabel.setVisibility(View.GONE);
        }


        //Places
        TextView placesTextViewLabel = (TextView) getActivity().findViewById(R.id.google_plus_user_info_places_label);
        LinearLayout placesLayout = (LinearLayout) getActivity().findViewById(R.id.google_plus_places_layout);
        placesLayout.removeAllViews();
        if (user.getPlacesLived() != null) {
            int counter = 1;
            for (Place o : user.getPlacesLived()) {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(18);
                textView.setText(Integer.toString(counter) + ") " + o.getValue());
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                textView.setPaintFlags(p.getColor());
                textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                counter++;
                final Place tempPlace = o;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + tempPlace.getValue()));
                        startActivity(intent);
                    }
                });
                placesLayout.addView(textView);
            }
        } else {
            placesLayout.setVisibility(View.GONE);
            placesTextViewLabel.setVisibility(View.GONE);
        }
    }
}