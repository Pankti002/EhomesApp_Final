package com.e_society.display;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.adapter.UserListAdapter;
import com.e_society.model.UserLangModel;
import com.e_society.update.UserUpdateActivity;
import com.e_society.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.e_society.R;
import com.e_society.utils.VolleySingleton;

import java.util.ArrayList;

public class UserDisplayActivity extends AppCompatActivity {

    ListView listview;
    FloatingActionButton btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);

        listview = findViewById(R.id.ls_User_listview);

        //add button
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDisplayActivity.this, UserUpdateActivity.class);
                startActivity(intent);
            }
        });
        DisplayUserApi();
    }

    private void DisplayUserApi() {
        ArrayList<UserLangModel> arrayList = new ArrayList<UserLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Display--onResponse:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject role = jsonObject1.getJSONObject("role");
                        String roleId = role.getString("_id");
                        Log.e("roleId in display:", roleId);
                        String strUserId = jsonObject1.getString("_id");
                        String strFirstName = jsonObject1.getString("firstName");
                        String strLastName = jsonObject1.getString("lastName");
                        String strDateOfBirth = jsonObject1.getString("dateOfBirth");
                        String strAge = jsonObject1.getString("age");
                        String strGender = jsonObject1.getString("gender");
                        String strContactNo = jsonObject1.getString("contactNo");
                        String strEmail = jsonObject1.getString("email");
                        String strPassword = jsonObject1.getString("password");


                        UserLangModel userLangModel = new UserLangModel();
                        userLangModel.set_id(strUserId);
                        userLangModel.setRoleId(roleId);
                        userLangModel.setFirstName(strFirstName);
                        userLangModel.setLastName(strLastName);
                        userLangModel.setDateOfBirth(strDateOfBirth);
                        userLangModel.setAge(strAge);
                        userLangModel.setGender(strGender);
                        userLangModel.setContactNo(strContactNo);
                        userLangModel.setEmail(strEmail);
                        userLangModel.setPassword(strPassword);
                        arrayList.add(userLangModel);

                    }
                    UserListAdapter userListAdapter = new UserListAdapter(UserDisplayActivity.this, arrayList);
                    listview.setAdapter(userListAdapter);
                    userListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", String.valueOf(error));
            }
        });

        VolleySingleton.getInstance(UserDisplayActivity.this).addToRequestQueue(stringRequest);

    }

}