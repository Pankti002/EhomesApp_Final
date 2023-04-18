package com.e_society.display;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.DashBoardActivity;
import com.e_society.LoginActivity;
import com.e_society.MaintenanceActivity;
import com.e_society.NonMemberActivity;
import com.e_society.R;
import com.e_society.SecurityDashboardActivity;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.MaintenanceListAdapter;
import com.e_society.adapter.NonMemberListAdapter;
import com.e_society.model.MaintenanceLangModel;
import com.e_society.model.NonMemberLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NonMemberDisplayActivity extends AppCompatActivity {
    ListView listview;
    FloatingActionButton btnAdd;

    String name;
    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        Log.e(name,"name in user Display");
        if(name.equals("user"))
        {
            Intent i = new Intent(NonMemberDisplayActivity.this, UserDashBoardActivity.class);
            startActivity(i);
        }
        else if(name.equals("admin")) {
            Intent i = new Intent(NonMemberDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        } else if(name.equals("security")) {
            Intent i = new Intent(NonMemberDisplayActivity.this, SecurityDashboardActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member_display);
        listview = findViewById(R.id.ls_nonMember_listview);

        //Update button
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonMemberDisplayActivity.this, NonMemberActivity.class);
                startActivity(intent);
            }
        });
        NonMemberApi();
    }

    private void NonMemberApi() {
        ArrayList<NonMemberLangModel> arrayList = new ArrayList<NonMemberLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.NONMEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("house");
                        String houseId = jsonObject2.getString("_id");
                        String houseDetails=jsonObject2.getString("houseDetails");

                        Log.e(houseId,"house id in display");

                        String strNonMemberId = jsonObject1.getString("_id");
                        String strNonMemberName = jsonObject1.getString("name");
                        String strTime = jsonObject1.getString("arrivingTime");
                        String strDate=jsonObject1.getString("date");
                        String strIsVisited = jsonObject1.getString("isVisited");
                        String strPickup = jsonObject1.getString("pickup");
                        String strDeliver = jsonObject1.getString("deliver");


                        NonMemberLangModel nonMemberLangModel = new NonMemberLangModel();
                        nonMemberLangModel.setId(strNonMemberId);
                        nonMemberLangModel.setHouseId(houseId);
                        nonMemberLangModel.setHouseName(houseDetails);
                        nonMemberLangModel.setNonMemberName(strNonMemberName);
                        nonMemberLangModel.setArrivingTime(strTime);
                        nonMemberLangModel.setDate(strDate);
                        nonMemberLangModel.setIsVisited(strIsVisited);
                        nonMemberLangModel.setPickup(strPickup);
                        nonMemberLangModel.setDeliver(strDeliver);
                        arrayList.add(nonMemberLangModel);

                    }
                    NonMemberListAdapter nonMemberListAdapter = new NonMemberListAdapter(NonMemberDisplayActivity.this, arrayList);
                    listview.setAdapter(nonMemberListAdapter);
                    nonMemberListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}