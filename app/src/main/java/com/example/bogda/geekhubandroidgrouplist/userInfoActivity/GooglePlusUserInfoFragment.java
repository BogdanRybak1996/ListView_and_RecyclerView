package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.content.Context;
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
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.GitHubUser;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.GooglePlusUser;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.Organization;
import com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser.Place;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.bogda.geekhubandroidgrouplist.service.OnlineChecker.isOnline;

/**
 * Created by bohdan on 04.11.16.
 */

public class GooglePlusUserInfoFragment extends Fragment {
    private GooglePlusUser user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_google_plus_user_info, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //get api url
        String[] urlParams = getActivity().getIntent().getData().toString().split("/");
        String apiUrl = "https://www.googleapis.com/plus/v1/people/" + urlParams[urlParams.length - 1] + "?key=AIzaSyDk23y7ndIvFdIWyTCbntt50Y8ZH-DCgoo";

        //get user object
        if (isOnline(getContext())) {
            final String[] jsonResult = {""};
            OkHttpClient client = new OkHttpClient();
            HttpUrl url = HttpUrl.parse(apiUrl);
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getContext(), "Data get error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonResult = response.body().string();
                    Gson gson = new Gson();
                    user = gson.fromJson(jsonResult, GooglePlusUser.class);
                    getActivity().runOnUiThread(updateUIRunnable);
                }
            });

        }
        else{
            Toast.makeText(getContext(),"Check the internet connection",Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
    }



    private final Runnable updateUIRunnable = new Runnable() {
        @Override
        public void run() {

            //Check user
            try {
                if (!user.getObjectType().equals("person")) {
                    Toast.makeText(getActivity(), "This is not Google+ user link", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    return;
                }
            }
            catch (NullPointerException e){
                Toast.makeText(getActivity(), "This is not Google+ user link", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                return;
            }

            //Cover photo
            if (user.getCover() != null) {
                ImageView coverImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_background);
                Picasso.with(getActivity()).load(user.getCover().getCoverPhoto().getUrl()).into(coverImageView);
            }

            //Main photo
            if (user.getImage() != null) {
                ImageView mainImageView = (ImageView) getActivity().findViewById(R.id.google_plus_user_info_image_main);
                String photoUrl = user.getImage().getUrl().replaceFirst("sz=50", "sz=200");
                Picasso.with(getActivity()).load(photoUrl).into(mainImageView);
            }

            //Name
            TextView nameTextView = (TextView) getActivity().findViewById(R.id.google_plus_user_info_name);
            nameTextView.setText(user.getDisplayName());

            //Click on google+ logo
            ImageView googleLogoImageView = (ImageView) getActivity().findViewById(R.id.google_plus_logo_image);
            final Uri googlePlusUrl = Uri.parse(user.getUrl());
            googleLogoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(googlePlusUrl);
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
    };
}