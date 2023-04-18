package com.e_society.display;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.DashBoardActivity;
import com.e_society.EventActivity;
import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.UserDashBoardActivity;
import com.e_society.adapter.EventListAdapter;
import com.e_society.model.EventLangModel;
import com.e_society.model.MaintenanceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EventDisplayActivity extends AppCompatActivity {

    ListView eventList;
    FloatingActionButton btnAdd;

    String name;

    @Override
    public void onBackPressed() {
        name= LoginActivity.getName();
        Log.e(name,"name in user Display");
        if(name.equals("user"))
        {
            Intent i = new Intent(EventDisplayActivity.this, UserDashBoardActivity.class);
            startActivity(i);
        }
        else if(name.equals("admin")) {
            Intent i = new Intent(EventDisplayActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);

        eventList = findViewById(R.id.ls_event_listview);

        btnAdd = findViewById(R.id.btn_add_event);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDisplayActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        EventApi();

    }

    private void EventApi() {
        ArrayList<EventLangModel> arrayList = new ArrayList<EventLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.EVENT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("house");
                        String houseId = jsonObject2.getString("_id");
                       String houseDetails=jsonObject2.getString("houseDetails");

                        JSONObject jsonObject3 = jsonObject1.getJSONObject("place");
                        String placeId = jsonObject3.getString("_id");
                        String palceName=jsonObject3.getString("placeName");

                        String strEventId = jsonObject1.getString("_id");
                        String strEventDate = jsonObject1.getString("eventDate");
                        String strEndDate = jsonObject1.getString("eventEndDate");
                        String strEventDetails = jsonObject1.getString("eventDetails");
                        String strRent = jsonObject1.getString("rent");

                        EventLangModel eventLangModel = new EventLangModel();
                        eventLangModel.set_id(strEventId);
                        eventLangModel.setHouseName(houseDetails);
                        eventLangModel.setPlaceName(palceName);
                        eventLangModel.setDate(strEventDate);
                        eventLangModel.setEventDate(strEndDate);
                        eventLangModel.setHouse_id(houseId);
                        eventLangModel.setPlace_id(placeId);
                        eventLangModel.setEventDetails(strEventDetails);
                        eventLangModel.setRent(strRent);
                        arrayList.add(eventLangModel);
                    }
                    EventListAdapter eventListAdapter = new EventListAdapter(EventDisplayActivity.this, arrayList);
                    eventList.setAdapter(eventListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(EventDisplayActivity.this).addToRequestQueue(stringRequest);
    }

}
