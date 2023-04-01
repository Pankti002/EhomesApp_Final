package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_data = findViewById(R.id.tv_data);

        Intent i = getIntent();
        String strData = i.getStringExtra("KEY_DATA");

        tv_data.setText("DEETS : "+ strData);
    }
}