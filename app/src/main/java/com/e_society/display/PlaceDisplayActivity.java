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
import com.e_society.adapter.PlaceListAdapter;
import com.e_society.model.PlaceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PlaceDisplayActivity extends AppCompatActivity {

    ListView listview;
    FloatingActionButton btnAddPLace;
    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        if(name.equals("admin")) {
            Intent i = new Intent(PlaceDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_display);
        listview = findViewById(R.id.ls_place_listview);

        //Update button
        btnAddPLace = findViewById(R.id.btn_Add_Place);
        btnAddPLace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDisplayActivity.this, PlaceActivity.class);
                startActivity(intent);
            }
        });

        PlaceApi();
    }

    private void PlaceApi() {
        ArrayList<PlaceLangModel> arrayList = new ArrayList<PlaceLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.PLACE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strPlaceId = jsonObject1.getString("_id");
                        String strPlaceName = jsonObject1.getString("placeName");
                        String strRent=jsonObject1.getString("rent");

                        PlaceLangModel placeLangModel = new PlaceLangModel();
                        placeLangModel.set_id(strPlaceId);
                        placeLangModel.setPlaceName(strPlaceName);
                        placeLangModel.setRent(strRent);
                        arrayList.add(placeLangModel);

                    }
                    PlaceListAdapter placeListAdapter = new PlaceListAdapter(PlaceDisplayActivity.this, arrayList);
                    listview.setAdapter(placeListAdapter);
                    placeListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(PlaceDisplayActivity.this).addToRequestQueue(stringRequest);
    }
}