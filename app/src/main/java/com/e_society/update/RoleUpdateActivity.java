package com.e_society.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.R;
import com.e_society.display.RoleDisplayActivity;
import com.e_society.model.RoleLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RoleUpdateActivity extends AppCompatActivity {

    EditText edtRoleName;
    Button btnRole, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        Intent i = getIntent();

        edtRoleName = findViewById(R.id.et_roleName);
        btnRole = findViewById(R.id.btn_role);
        btnDelete = findViewById(R.id.btn_delete_role);

        String strRoleId = i.getStringExtra("ROLE_ID");
        String strRoleName = i.getStringExtra("ROLE_NAME");

        RoleLangModel roleLangModel = new RoleLangModel();
        edtRoleName.setText(strRoleName);

        btnRole.setText("Update Role");
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAPI(strRoleId);
            }
        });
        btnRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRoleName = edtRoleName.getText().toString();
                if (strRoleName.length() == 0) {
                    edtRoleName.requestFocus();
                    edtRoleName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strRoleName.matches("[a-zA-Z ]+")) {
                    edtRoleName.requestFocus();
                    edtRoleName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else {
                    Toast.makeText(RoleUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    RoleApi(strRoleId, strRoleName);
                }
            }
        });


    }

    private void deleteAPI(String roleId) {
        Log.e("TAG****", "deleteAPI Update " + roleId);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.ROLE_URL + "/" + roleId, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(RoleUpdateActivity.this, RoleDisplayActivity.class);
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
                hashMap.put("roleId", roleId);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(RoleUpdateActivity.this).addToRequestQueue(stringRequest);


    }


    private void RoleApi(String strRoleId, String strRoleName) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.ROLE_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(RoleUpdateActivity.this, RoleDisplayActivity.class);
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
                Log.e("id in update Map : ", strRoleId);
                hashMap.put("roleId", strRoleId);
                hashMap.put("roleName", strRoleName);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(RoleUpdateActivity.this).addToRequestQueue(stringRequest);

    }

}