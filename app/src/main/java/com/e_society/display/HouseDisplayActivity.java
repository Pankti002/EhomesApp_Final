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
import com.e_society.HouseActivity;
import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.HouseListAdapter;
import com.e_society.model.HouseLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HouseDisplayActivity extends AppCompatActivity {

    ListView listview;
    FloatingActionButton btnAdd;
    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        if(name.equals("admin")) {
            Intent i = new Intent(HouseDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_display);
        listview = findViewById(R.id.ls_house_listview);

        btnAdd = findViewById(R.id.btn_add_house);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseDisplayActivity.this, HouseActivity.class);
                startActivity(intent);
            }
        });
        HouseApi();
    }


    private void HouseApi() {
        ArrayList<HouseLangModel> arrayList = new ArrayList<HouseLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.HOUSE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                        String strUserId = jsonObject2.getString("_id");


                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        HouseLangModel houseLangModel = new HouseLangModel();
                        houseLangModel.set_id(strHouseId);
                        houseLangModel.setHouse(strHouseDeets);
                        houseLangModel.setUser(strUserId);
                        arrayList.add(houseLangModel);
                    }
                    HouseListAdapter houseListAdapter = new HouseListAdapter(HouseDisplayActivity.this, arrayList);
                    listview.setAdapter(houseListAdapter);
                    houseListAdapter.notifyDataSetChanged();
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