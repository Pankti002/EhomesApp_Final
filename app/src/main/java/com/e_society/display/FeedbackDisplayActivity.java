package com.e_society.display;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.e_society.DashBoardActivity;
import com.e_society.FeedbackActivity;
import com.e_society.LoginActivity;
import com.e_society.R;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.FeedbackListAdapter;
import com.e_society.model.FeedbackLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FeedbackDisplayActivity extends AppCompatActivity {

    ListView feedbackList;
    FloatingActionButton btnFeedbackAdd;
    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        Log.e(name,"name in user Display");
        if(name.equals("user"))
        {
            Intent i = new Intent(FeedbackDisplayActivity.this, UserDashBoardActivity.class);
            startActivity(i);
        }
        else if(name.equals("admin")) {
            Intent i = new Intent(FeedbackDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_display);

        feedbackList = findViewById(R.id.feedback_listview);

        btnFeedbackAdd = findViewById(R.id.btn_feedbackAdd);
        btnFeedbackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackDisplayActivity.this , FeedbackActivity.class);
                startActivity(intent);
            }
        });
        FeedbackApi();
    }



    private void FeedbackApi() {
        ArrayList<FeedbackLangModel> arrayList=new ArrayList<FeedbackLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.FEEDBACK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject2 =jsonObject1.getJSONObject("house");
                        String strHouseId=jsonObject2.getString("_id");
                        String houseDetails=jsonObject2.getString("houseDetails");


                        String strFeedbackId = jsonObject1.getString("_id");
                        String strFeedback=jsonObject1.getString("feedback");
                        String strDate=jsonObject1.getString("date");
                        String strAcknowledgement = jsonObject1.getString("acknowledgement");

                        FeedbackLangModel feedbackLangModel = new FeedbackLangModel();
                        feedbackLangModel.setDate(strDate);
                        feedbackLangModel.setHouseId(strHouseId);
                        feedbackLangModel.setHouseName(houseDetails);
                        feedbackLangModel.set_id(strFeedbackId);
                        feedbackLangModel.setFeedback(strFeedback);
                        feedbackLangModel.setAcknowledgement(strAcknowledgement);
                        arrayList.add(feedbackLangModel);
                    }
                    FeedbackListAdapter feedbackListAdapter = new FeedbackListAdapter(FeedbackDisplayActivity.this,arrayList);
                    feedbackList.setAdapter(feedbackListAdapter);

                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(FeedbackDisplayActivity.this).addToRequestQueue(stringRequest);
    }
}