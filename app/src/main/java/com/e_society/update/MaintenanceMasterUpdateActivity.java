package com.e_society.update;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.R;
import com.e_society.display.MaintenanceMasterDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class MaintenanceMasterUpdateActivity extends AppCompatActivity {
    EditText edtAmount,edtPenalty;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_master);

        Intent i =getIntent();

        edtAmount=findViewById(R.id.edt_maintenanceAmount);
        edtPenalty=findViewById(R.id.edt_penalty);

        String masterId = i.getStringExtra("MASTER_ID");
        String strMasterAmount = i.getStringExtra("MAINTENANCE_AMOUNT");
        String strPenalty=i.getStringExtra("PENALTY");

        edtAmount.setText(strMasterAmount);
        edtPenalty.setText(strPenalty);


        btnAdd=findViewById(R.id.btn_addMaster);
        btnAdd.setText("Update Maintenance");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAmount=edtAmount.getText().toString();
                String strPenalty=edtPenalty.getText().toString();

                Log.e(strAmount,strPenalty+" "+masterId);

                if (strAmount.length() == 0) {
                    edtAmount.requestFocus();
                    edtAmount.setError("FIELD CANNOT BE EMPTY");
                } else if (!strAmount.matches("^[0-9]+$")) {
                    edtAmount.requestFocus();
                    edtAmount.setError("PLEASE ENTER DIGITS ONLY");
                }else if (strPenalty.length() == 0) {
                    edtPenalty.requestFocus();
                    edtPenalty.setError("FIELD CANNOT BE EMPTY");
                } else if (!strPenalty.matches("^[0-9]+$")) {
                    edtPenalty.requestFocus();
                    edtPenalty.setError("PLEASE ENTER DIGITS ONLY");
                }
                else {
                    apiCall(masterId, strAmount, strPenalty);
                }

            }
        });


    }

    private void apiCall(String masterId, String strAmount, String strPenalty) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.MAINTENANCE_MASTER_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("Update api calling done", response);
                Intent intent = new Intent(MaintenanceMasterUpdateActivity.this, MaintenanceMasterDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                Log.e(masterId+" ","id");
                hashMap.put("maintenanceMasterId", masterId);
                hashMap.put("maintenanceAmount", strAmount);
                hashMap.put("penalty", strPenalty);


                return hashMap;
            }
        };
        VolleySingleton.getInstance(MaintenanceMasterUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}
