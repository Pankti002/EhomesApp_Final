package com.e_society;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MaintenanceMasterUpdateActivity extends AppCompatActivity {
    EditText edtAmount,edtPenalty;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_master);

        btnAdd=findViewById(R.id.btn_addMaster);
        btnAdd.setText("Update Maintenance");


    }
}
