package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.e_society.adapter.MyListAdapter;
import com.e_society.display.EventDisplayActivity;
import com.e_society.display.FeedbackDisplayActivity;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.display.MemberDisplayActivity;
import com.e_society.display.NonMemberDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.display.UserDisplayActivity;
import com.e_society.model.LangModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserDashBoardActivity extends AppCompatActivity {
    String name;
    GridView gridView;

    String strData[] = {"User", "Member", "Event", "Maintenance", "Feedback", "NonMember"};

    int imgData[] = {R.drawable.user, R.drawable.member, R.drawable.event,
            R.drawable.maintanence, R.drawable.ic_feedback, R.drawable.ic_nonmember};

    ArrayList<LangModel> langModelArrayList;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        Log.e(name,"name in user Display");
        if(name.equals("user")) {
            Intent i = new Intent(UserDashBoardActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        gridView = findViewById(R.id.grid_view);
        langModelArrayList = new ArrayList<LangModel>();

        for (int i = 0; i < strData.length; i++) {
            LangModel langModel = new LangModel(strData[i], imgData[i]);
            langModelArrayList.add(langModel);
        }

        MyListAdapter myListAdapter = new MyListAdapter(this, langModelArrayList);
        gridView.setAdapter(myListAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {


                    Intent intent = new Intent(UserDashBoardActivity.this, UserDisplayActivity.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(UserDashBoardActivity.this, MemberDisplayActivity.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(UserDashBoardActivity.this, EventDisplayActivity.class);
                    startActivity(intent);

                } else if (i == 3) {
                    Intent intent = new Intent(UserDashBoardActivity.this, MaintenanceDisplayActivity.class);
                    startActivity(intent);

                } else if (i == 4) {
                    Intent intent = new Intent(UserDashBoardActivity.this, FeedbackDisplayActivity.class);
                    startActivity(intent);

                } else if (i == 5) {
                    Intent intent = new Intent(UserDashBoardActivity.this, NonMemberDisplayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}