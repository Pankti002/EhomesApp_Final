package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.e_society.display.AdminDisplayActivity;
import com.e_society.display.EventDisplayActivity;
import com.e_society.display.FeedbackDisplayActivity;
import com.e_society.display.HouseDisplayActivity;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.display.MaintenanceMasterDisplayActivity;
import com.e_society.display.MemberDisplayActivity;
import com.e_society.display.NonMemberDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.display.RoleDisplayActivity;
import com.e_society.display.StaffDisplayActivity;
import com.e_society.display.UserDisplayActivity;
import com.e_society.update.HouseUpdateActivity;
import com.e_society.update.StaffUpdateAcivity;


public class MainActivity extends AppCompatActivity {

    int time = 3000;
    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gifImageView = findViewById(R.id.gif);
        gifImageView.setGifImageResource(R.drawable.enhomes);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, time);
    }
}