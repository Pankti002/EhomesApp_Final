package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.e_society.adapter.MyListAdapter;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.display.RoleDisplayActivity;
import com.e_society.display.UserDisplayActivity;
import com.e_society.model.LangModel;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    GridView gridView;
    String strData[] = {"Role", "User", "Member", "Event", "Maintenance", "Staff", "FeedBack"
            , "NonMember", "House", "Place"};
    int imgData[] = {R.drawable.role, R.drawable.user, R.drawable.member, R.drawable.event,
            R.drawable.maintanence, R.drawable.staff, R.drawable.ic_feedback, R.drawable.ic_nonmember, R.drawable.ic_house, R.drawable.place};

    ArrayList<LangModel> langModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);

        gridView = findViewById(R.id.grid_view);
        langModelArrayList = new ArrayList<LangModel>();

        for (int i = 0; i < strData.length; i++) {
            LangModel langModel = new LangModel(strData[i], imgData[i]);
            langModelArrayList.add(langModel);
        }

        MyListAdapter myListAdapter = new MyListAdapter(this, langModelArrayList);
        gridView.setAdapter(myListAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(DashBoardActivity.this, RoleDisplayActivity.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(DashBoardActivity.this, UserDisplayActivity.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(DashBoardActivity.this, MemberActivity.class);
                    startActivity(intent);

                } else if (i == 3) {
                    Intent intent = new Intent(DashBoardActivity.this, EventActivity.class);
                    startActivity(intent);

                } else if (i == 4) {
                    Intent intent = new Intent(DashBoardActivity.this, MaintenanceDisplayActivity.class);
                    startActivity(intent);

                } else if (i == 5) {
                    Intent intent = new Intent(DashBoardActivity.this, StaffActivity.class);
                    startActivity(intent);

                } else if (i == 6) {
                    Intent intent = new Intent(DashBoardActivity.this, FeedbackActivity.class);
                    startActivity(intent);

                } else if (i == 7) {
                    Intent intent = new Intent(DashBoardActivity.this, NonMemberActivity.class);
                    startActivity(intent);

                } else if (i == 8) {
                    Intent intent = new Intent(DashBoardActivity.this, HouseActivity.class);
                    startActivity(intent);

                } else if (i == 9) {
                    Intent intent = new Intent(DashBoardActivity.this, PlaceDisplayActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}