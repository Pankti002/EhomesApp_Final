package com.e_society;

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
import com.e_society.display.RoleDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RoleActivity extends AppCompatActivity {
    EditText edtRoleName;
    Button btnRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        btnRole = findViewById(R.id.btn_role);
        edtRoleName = findViewById(R.id.et_roleName);

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
                    Toast.makeText(RoleActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    Log.e("roleName : ", strRoleName);
                    roleApi(strRoleName);
                }
            }
        });
    }

    private void roleApi(String strRoleName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.ROLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api calling done: ", response);
                Intent intent = new Intent(RoleActivity.this, RoleDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("roleName", strRoleName);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(RoleActivity.this).addToRequestQueue(stringRequest);
    }
}