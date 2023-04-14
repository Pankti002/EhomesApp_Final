package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.adapter.EventListAdapter;
import com.e_society.adapter.PlaceListAdapter;
import com.e_society.display.EventDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.model.EventLangModel;
import com.e_society.model.PlaceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    EditText edtEventDetails, edtPlaceId, edtHouseId;
    Button btnAddEvent;
    TextView tvDate, tvEndDate, tvRent;
    ImageButton btnDate, btnEndDate;

    String strPlaceId, strDate, strEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        tvDate = findViewById(R.id.tv_date);
        tvEndDate = findViewById(R.id.tv_eventEndDate);

        edtEventDetails = findViewById(R.id.edt_eventDetail);
        tvRent = findViewById(R.id.edt_rent);
        edtHouseId = findViewById(R.id.edt_eHouseId);
        edtPlaceId = findViewById(R.id.edt_ePlaceId);

        tvRent.setText("200");

        //date
        btnDate = findViewById(R.id.btn_date);
        btnEndDate = findViewById(R.id.btn_endDate);
        tvDate = findViewById(R.id.tv_date);
        tvEndDate = findViewById(R.id.tv_eventEndDate);

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

//        getPlaceApi();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        //txtDate.setText(strDate);


                        tvDate.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strEndDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strEndDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvEndDate.setText(strEndDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });


        //add event
        btnAddEvent = findViewById(R.id.btn_event);



        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("inside","button Click");
                String strHouseId = edtHouseId.getText().toString();
                strPlaceId = edtPlaceId.getText().toString();
                strDate = tvDate.getText().toString();
                strEndDate = tvEndDate.getText().toString();
                String strEventDetails = edtEventDetails.getText().toString();
                String strRent = tvRent.getText().toString();

                Log.e("get Text","done");

                if(strDate.length()==0)
                {
                    tvDate.requestFocus();
                    tvDate.setError("FIELD CANNOT BE EMPTY");
                }
                else if(strEndDate.length()==0)
                {
                    tvEndDate.requestFocus();
                    tvEndDate.setError("FIELD CANNOT BE EMPTY");
                }
                else if((strDate.length()!=0) && (strEndDate.length()!=0))
                {
                    Log.e("check date"," api call");
                    getEventsApi();
                }
                else if(strEventDetails.length()==0)
                {
                    edtEventDetails.requestFocus();
                    edtEventDetails.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strEventDetails.matches("[a-zA-Z ]+"))
                {
                    edtEventDetails.requestFocus();
                    edtEventDetails.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if (strRent.length() ==0) {
                    tvRent.requestFocus();
                    tvRent.setError("FIELD CANNOT BE EMPTY");
                }
                else{
                    Toast.makeText(EventActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();
                    apiCall(strHouseId, strPlaceId, strDate, strEndDate, strEventDetails, strRent);

                }

            }


        });

    }

    private void getEventsApi() {
        ArrayList<EventLangModel> arrayList = new ArrayList<EventLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.EVENT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("api calling done","");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        JSONObject jsonObject3 = jsonObject1.getJSONObject("place");
                        String placeId = jsonObject3.getString("_id");

                        String strEventDate = jsonObject1.getString("eventDate");
                        String strEventEndDate = jsonObject1.getString("eventEndDate");

                        Log.e(strPlaceId,strDate+" "+strEventDate);
                        Log.e(strPlaceId.equals(placeId)+"","");
                        Log.e(placeId,"");
                        if(strPlaceId.equals(placeId)==true)
                        {
                            Log.e("inside","if");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date EventDate = sdf.parse(strEventDate);
                            Date date1 = sdf.parse(strDate);
                            Log.e(EventDate.compareTo(date1)+"","");
                            if(strEventDate.equals(strDate)==true || strEventDate.equals(strEndDate)==true || strEventEndDate.equals(strDate)==true || strEventEndDate.equals(strEndDate)==true){
                                Log.e("inside","another if");
                                edtPlaceId.requestFocus();
                                tvDate.requestFocus();
                                tvEndDate.requestFocus();
                                edtPlaceId.setError("PLACE IS NOT AVAILABLE ON SELECTED DATES");
                                Log.e("PLACE IS NOT AVAILABLE"," ON SELECTED DATES");
                            }
                        }

                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(EventActivity.this).addToRequestQueue(stringRequest);

    }

//    private void getPlaceApi() {
//
//        ArrayList<PlaceLangModel> arrayList = new ArrayList<PlaceLangModel>();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.PLACE_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("TAG", "onResponse:" + response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        String strPlaceId = jsonObject1.getString("_id");
//                        String strPlaceName = jsonObject1.getString("placeName");
//                        String strRent=jsonObject1.getString("rent");
//
//
//                        if(strSelectedPlaceName.equals(strPlaceName))
//                        {
//                            tvRent.setText(strRent);
//                        }
//
//
//
//                    }
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
//        VolleySingleton.getInstance(EventActivity.this).addToRequestQueue(stringRequest);
//    }

    private void apiCall(String strHouseId, String strPlaceId, String strDate, String strEndDate, String strEventDetails, String strRent) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.EVENT_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);

                Intent intent = new Intent(EventActivity.this, EventDisplayActivity.class);

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
                hashMap.put("house", strHouseId);
                hashMap.put("place", strPlaceId);
                hashMap.put("eventDate", strDate);
                hashMap.put("eventEndDate", strEndDate);
                hashMap.put("eventDetails", strEventDetails);
                hashMap.put("rent", strRent);
                return hashMap;

            }
        };
        VolleySingleton.getInstance(EventActivity.this).addToRequestQueue(stringRequest);

    }
}










