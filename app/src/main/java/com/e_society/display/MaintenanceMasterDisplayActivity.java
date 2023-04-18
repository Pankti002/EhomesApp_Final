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
import com.e_society.PlaceActivity;
import com.e_society.R;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.MaintenanceMasterListAdapter;
import com.e_society.adapter.PlaceListAdapter;
import com.e_society.model.MaintenanceMasterLangModel;
import com.e_society.model.PlaceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MaintenanceMasterDisplayActivity extends AppCompatActivity {

    ListView listview;
    String name;


    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        Log.e(name,"name in user Display");
         if(name.equals("admin")) {
            Intent i = new Intent(MaintenanceMasterDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_master_display);

        listview = findViewById(R.id.ls_maintenanceMaster_listview);

       MaintenanceMasterApi();

    }

    private void MaintenanceMasterApi() {
        ArrayList<MaintenanceMasterLangModel> arrayList = new ArrayList<MaintenanceMasterLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.MAINTENANCE_MASTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strMasterId = jsonObject1.getString("_id");
                        String strMasterAmount = jsonObject1.getString("maintenanceAmount");
                        String strPenalty=jsonObject1.getString("penalty");

                        MaintenanceMasterLangModel maintenanceMasterLangModel=new MaintenanceMasterLangModel();
                        maintenanceMasterLangModel.set_id(strMasterId);
                        maintenanceMasterLangModel.setMaintenanceAmount(strMasterAmount);
                        maintenanceMasterLangModel.setPenalty(strPenalty);
                        arrayList.add(maintenanceMasterLangModel);

                    }
                    MaintenanceMasterListAdapter maintenanceMasterListAdapter = new MaintenanceMasterListAdapter(MaintenanceMasterDisplayActivity.this, arrayList);
                    listview.setAdapter(maintenanceMasterListAdapter);
                    maintenanceMasterListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(MaintenanceMasterDisplayActivity.this).addToRequestQueue(stringRequest);

    }
}