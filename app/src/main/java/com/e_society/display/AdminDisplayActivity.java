package com.e_society.display;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.AddAdminActivity;
import com.e_society.DashBoardActivity;
import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.RoleActivity;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.AdminListAdapter;
import com.e_society.adapter.MyRoleAdapter;
import com.e_society.model.AdminLangModel;
import com.e_society.model.RoleLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDisplayActivity extends AppCompatActivity {

    ListView listview;
    FloatingActionButton btnAdd;
    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        if(name.equals("admin")) {
            Intent i = new Intent(AdminDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display);
        listview = findViewById(R.id.ls_admin_listview);
        btnAdd = findViewById(R.id.btn_add_admin);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDisplayActivity.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });
        AdminApi();
    }

    private void AdminApi() {
        ArrayList<AdminLangModel> arrayList = new ArrayList<AdminLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.ADMIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strAdminId = jsonObject1.getString("_id");
                        String strEmail = jsonObject1.getString("email");
                        String strPassword = jsonObject1.getString("password");

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("role");
                        String roleId = jsonObject2.getString("_id");

                        AdminLangModel roleLangModel = new AdminLangModel();
                        roleLangModel.set_id(strAdminId);
                        roleLangModel.setEmail(strEmail);
                        roleLangModel.setPassword(strPassword);
                        roleLangModel.setRoleId(roleId);

                        arrayList.add(roleLangModel);
                    }
                    AdminListAdapter myListAdapter = new AdminListAdapter(AdminDisplayActivity.this, arrayList);
                    listview.setAdapter(myListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(AdminDisplayActivity.this).addToRequestQueue(stringRequest);

    }
}