package com.e_society.display;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.DashBoardActivity;
import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.RoleActivity;
import com.e_society.adapter.MyRoleAdapter;
import com.e_society.model.RoleLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoleDisplayActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton btnAdd;
    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        if(name.equals("admin")) {
            Intent i = new Intent(RoleDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_display);

        listView = findViewById(R.id.ls_listview);
        btnAdd = findViewById(R.id.btn_addRole);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoleDisplayActivity.this, RoleActivity.class);
                startActivity(intent);
            }
        });
        RoleApi();

    }

    private void RoleApi() {
        ArrayList<RoleLangModel> arrayList = new ArrayList<RoleLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.ROLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strRoleId = jsonObject1.getString("_id");
                        String strRoleName = jsonObject1.getString("roleName");

                        RoleLangModel roleLangModel = new RoleLangModel();
                        roleLangModel.setRoleName(strRoleName);
                        roleLangModel.set_id(strRoleId);

                        arrayList.add(roleLangModel);
                    }
                    MyRoleAdapter myListAdapter = new MyRoleAdapter(RoleDisplayActivity.this, arrayList);
                    listView.setAdapter(myListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(RoleDisplayActivity.this).addToRequestQueue(stringRequest);
    }

}