package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.utils.Utils;

public class MaintenanceMasterActivity extends AppCompatActivity {

    EditText edtAmount,edtPenalty;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_master);

        edtAmount=findViewById(R.id.edt_maintenanceAmount);
        edtPenalty=findViewById(R.id.edt_penalty);

        btnAdd=findViewById(R.id.btn_addMaster);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strAmount=edtAmount.getText().toString();
                String strPenalty=edtPenalty.getText().toString();


                addMasterApi(strAmount,strPenalty);
            }
        });



    }

    private void addMasterApi(String strAmount, String strPenalty) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Utils.MAINTENANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

        };

    }
}