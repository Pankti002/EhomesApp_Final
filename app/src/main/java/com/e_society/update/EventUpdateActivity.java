package com.e_society.update;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.format.Time;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.EventActivity;
import com.e_society.R;
import com.e_society.display.EventDisplayActivity;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.model.EventLangModel;
import com.e_society.model.HouseLangModel;
import com.e_society.model.PlaceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class EventUpdateActivity extends AppCompatActivity {

    EditText edt_eventDetail;
    TextView tv_HouseId, tv_PlaceId;
    Button btn_event, btnDeleteEvent;
    TextView tvDate, tvEndDate, tvRent;
    ImageButton btnDate, btnEndDate;

    String houseId,placeId,strDate,strEndDate, strDetails,rent;
    Spinner spinnerHouse, spinnerPlace;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent i = getIntent();

        edt_eventDetail = findViewById(R.id.edt_eventDetail);
        tvRent = findViewById(R.id.edt_rent);
        btn_event = findViewById(R.id.btn_event);
        btnDate = findViewById(R.id.btn_date);
        tvDate = findViewById(R.id.tv_date);
        tvEndDate = findViewById(R.id.tv_eventEndDate);
        btnEndDate = findViewById(R.id.btn_endDate);
        tv_HouseId = findViewById(R.id.edt_HouseId);
        tv_PlaceId = findViewById(R.id.edt_PlaceId);
        btnDeleteEvent = findViewById(R.id.btn_delete_event);

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        spinnerHouse=findViewById(R.id.spinner_house);
        spinnerPlace=findViewById(R.id.spinner_place);
        spinnerHouse.setVisibility(View.GONE);
        spinnerPlace.setVisibility(View.GONE);

        String strEventDate = i.getStringExtra("EVENT_DATE");
        String strEventEndDate = i.getStringExtra("EVENT_END_DATE");
        String strEventDetails = i.getStringExtra("EVENT_DETAILS");
        String strRent = i.getStringExtra("RENT");
        String strEventId = i.getStringExtra("EVENT_ID");
        houseId = i.getStringExtra("HOUSE_ID");
        placeId = i.getStringExtra("PLACE_ID");



        getPlaceApi();
        getHouseApi();




        //set text
        EventLangModel eventLangModel = new EventLangModel();
        edt_eventDetail.setText(strEventDetails);
        tvRent.setText(strRent);
        tvDate.setText(strEventDate);
        tvEndDate.setText(strEventEndDate);


        btnDeleteEvent.setVisibility(View.VISIBLE);
        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAPI(strEventId);
            }
        });
//        btn_event.setText("Update Event");
//        btn_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                strDate = tvEndDate.getText().toString();
//                strEndDate = tvDate.getText().toString();
//                strDetails = edt_eventDetail.getText().toString();
//                rent = tvRent.getText().toString();
//
//                if(strEventDate.length()==0)
//                {
//                    tvDate.requestFocus();
//                    tvDate.setError("FIELD CANNOT BE EMPTY");
//                }
//                else if(strEventEndDate.length()==0)
//                {
//                    tvEndDate.requestFocus();
//                    tvEndDate.setError("FIELD CANNOT BE EMPTY");
//                }
//                else if(strEventDetails.length()==0)
//                {
//                    edt_eventDetail.requestFocus();
//                    edt_eventDetail.setError("FIELD CANNOT BE EMPTY");
//                }
//                else if(!strEventDetails.matches("[a-zA-Z ]+"))
//                {
//                    edt_eventDetail.requestFocus();
//                    edt_eventDetail.setError("ENTER ONLY ALPHABETICAL CHARACTER");
//                }
//                else{
//                    DateAPI(strEventDate,strEventEndDate,placeId);
//
//                }
//
//
//            }
//        });


        //date
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        tvDate.setText(strDate);
                    }
                },  year, month, date);
                datePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strEndDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strEndDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        tvEndDate.setText(strEndDate);

                    }
                },year, month, date);
                datePickerDialog.show();
            }
        });


    }

//    private void DateAPI(String strDate, String strEndDate, String placeId) {
//        ArrayList<EventLangModel> arrayList = new ArrayList<EventLangModel>();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.DATE_URL+"/"+ strDate +"/"+strEndDate+"/"+placeId, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String msg = jsonObject.getString("msg");
//                    Log.e(msg,"Message");
//                    if(msg.contains("No"))
//                    {
//                        Toast.makeText(EventUpdateActivity.this, "PLACE AVAILABLE", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(EventUpdateActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();
//                        apiCall(houseId, placeId, strDate, strEndDate, strDetails, rent);
//                    }
//                    else if(msg.contains("Events")){
//                        Toast.makeText(EventUpdateActivity.this, "PLACE NOT AVAILABLE", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        VolleySingleton.getInstance(EventUpdateActivity.this).addToRequestQueue(stringRequest);
//    }

    private void getHouseApi() {
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
                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        if(houseId.equals(strHouseId))
                        {
                            tv_HouseId.setText(strHouseDeets);
                            tv_HouseId.setVisibility(View.VISIBLE);
                        }
                    }

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

    private void getPlaceApi() {
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

                        if(placeId.equals(strPlaceId))
                        {
                            tv_PlaceId.setText(strPlaceName);
                            tv_PlaceId.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(EventUpdateActivity.this).addToRequestQueue(stringRequest);


    }

    private void deleteAPI(String id) {

        Log.e("TAG****", "deleteAPI Update " + id);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.EVENT_URL + "/" + id, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(EventUpdateActivity.this, EventDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("eventId", id);
                return hashMap;

            }
        };
        VolleySingleton.getInstance(EventUpdateActivity.this).addToRequestQueue(stringRequest);


    }


//    private void apiCall(String strEventID, String placeId ,String strEventDate, String strEventEndDate, String strEventDetails, String strRent) {
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.EVENT_URL, new Response.Listener<String>() {
//            @Override
//
//            public void onResponse(String response) {
//                Log.e("api calling done", response);
//                Intent intent = new Intent(EventUpdateActivity.this, EventDisplayActivity.class);
//                startActivity(intent);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error: ", String.valueOf(error));
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> hashMap = new HashMap<>();
//                Log.e("id in update map:", strEventID);
//                hashMap.put("eventId", strEventID);
//                hashMap.put("eventDate", strEventDate);
//                hashMap.put("eventEndDate", strEventEndDate);
//                hashMap.put("eventDetails", strEventDetails);
//                hashMap.put("rent", strRent);
//
//                return hashMap;
//
//
//            }
//        };
//        VolleySingleton.getInstance(EventUpdateActivity.this).addToRequestQueue(stringRequest);
//
//    }
}
