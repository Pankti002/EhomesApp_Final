package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.e_society.adapter.MyListAdapter;
import com.e_society.model.LangModel;

import java.util.ArrayList;

public class SecurityDashboardActivity extends AppCompatActivity {
    GridView gridView;

    String strData[] = {"Staff", "NonMember"};

    int imgData[] = {R.drawable.staff, R.drawable.ic_nonmember};

    ArrayList<LangModel> langModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_dashboard);

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
                    Intent intent = new Intent(SecurityDashboardActivity.this, StaffActivity.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(SecurityDashboardActivity.this, NonMemberActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
