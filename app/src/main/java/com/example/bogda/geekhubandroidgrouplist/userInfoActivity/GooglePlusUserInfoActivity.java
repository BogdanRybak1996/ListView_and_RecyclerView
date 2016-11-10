package com.example.bogda.geekhubandroidgrouplist.userInfoActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.Receivers.ChargeReceiver;
import com.example.bogda.geekhubandroidgrouplist.Receivers.HeadPhoneReceiver;

public class GooglePlusUserInfoActivity extends AppCompatActivity {

    HeadPhoneReceiver headphoneReceiver = new HeadPhoneReceiver();
    ChargeReceiver chargeReceiver = new ChargeReceiver();
    IntentFilter headphonesFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter chargeConnectedFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
    IntentFilter chargeDisconnectedFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_plus_user_info);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_google_plus_user_info, new GooglePlusUserInfoFragment()).commit();
        }
        getSupportActionBar().setTitle("Google+ user info: " + getIntent().getStringExtra("name"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(headphoneReceiver, headphonesFilter);
        registerReceiver(chargeReceiver, chargeConnectedFilter);
        registerReceiver(chargeReceiver, chargeDisconnectedFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(headphoneReceiver);
        unregisterReceiver(chargeReceiver);
    }

}
