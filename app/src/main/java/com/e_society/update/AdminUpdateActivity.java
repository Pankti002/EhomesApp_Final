package com.e_society.update;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.R;
import com.e_society.display.AdminDisplayActivity;
import com.e_society.display.RoleDisplayActivity;
import com.e_society.model.AdminLangModel;
import com.e_society.model.RoleLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        Intent i = getIntent();

        edtEmail = findViewById(R.id.edt_admin_email);
        edtPassword=findViewById(R.id.edt_admin_password);
        btnAdd = findViewById(R.id.btn_add_admin);
        btnDelete = findViewById(R.id.btn_delete_admin);

        edtPassword.setVisibility(View.GONE);

        String strAdminId = i.getStringExtra("ADMIN_ID");
        String strEmail = i.getStringExtra("EMAIL");

        AdminLangModel adminLangModel = new AdminLangModel();
        edtEmail.setText(strEmail);

        btnAdd.setText("Update Admin");
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAPI(strAdminId);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtEmail.getText().toString();
                if (strEmail.length() == 0) {
                    edtEmail.requestFocus();
                    edtEmail.setError("FIELD CANNOT BE EMPTY");
                } else if (!strEmail.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{0,4}$")) {
                    edtEmail.requestFocus();
                    edtEmail.setError("ENTER VAILD EMAIL");
                } else {
                    Toast.makeText(AdminUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    UpdateAdminApi(strAdminId, strEmail);
                }
            }
        });


    }

    private void deleteAPI(String adminId) {
        Log.e("TAG****", "deleteAPI Update " + adminId);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.ADMIN_URL + "/" + adminId, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(AdminUpdateActivity.this, AdminDisplayActivity.class);
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
                hashMap.put("id", adminId);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(AdminUpdateActivity.this).addToRequestQueue(stringRequest);


    }


    private void UpdateAdminApi(String strAdminId, String strEmail) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.ADMIN_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(AdminUpdateActivity.this, AdminDisplayActivity.class);
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
                Log.e("id in update Map : ", strAdminId);
                hashMap.put("id", strAdminId);
                hashMap.put("email", strEmail);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(AdminUpdateActivity.this).addToRequestQueue(stringRequest);

    }

}