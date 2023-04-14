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
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;


public class EventUpdateActivity extends AppCompatActivity {

    EditText edt_eventDetail, edt_HouseId, edtPlaceId;
    Button btn_event, btnDeleteEvent;
    TextView tvDate, tvEndDate, tvRent;
    ImageButton btnDate, btnEndDate;

    private int date;
    private int month;
    private int year;
    private String id;

    public EventUpdateActivity() {
    }


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
        edt_HouseId = findViewById(R.id.edt_eHouseId);
        edtPlaceId = findViewById(R.id.edt_ePlaceId);
        btnDeleteEvent = findViewById(R.id.btn_delete_event);

        String strEventDate = i.getStringExtra("EVENT_DATE");
        String strEventEndDate = i.getStringExtra("EVENT_END_DATE");
        String strEventDetails = i.getStringExtra("EVENT_DETAILS");
        String strRent = i.getStringExtra("RENT");
        String strEventId = i.getStringExtra("EVENT_ID");
        String strHouseId = i.getStringExtra("HOUSE_ID");
        String strPlaceId = i.getStringExtra("PLACE_ID");


        edt_HouseId.setText(strHouseId);
        edtPlaceId.setText(strPlaceId);


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
        btn_event.setText("Update Event");
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEventDate = tvEndDate.getText().toString();
                String strEventEndDate = tvDate.getText().toString();
                String strEventDetails = edt_eventDetail.getText().toString();
                String strRent = tvRent.getText().toString();

                if(strEventDate.length()==0)
                {
                    tvDate.requestFocus();
                    tvDate.setError("FIELD CANNOT BE EMPTY");
                }
                else if(strEventEndDate.length()==0)
                {
                    tvEndDate.requestFocus();
                    tvEndDate.setError("FIELD CANNOT BE EMPTY");
                }
                else if(strEventDetails.length()==0)
                {
                    edt_eventDetail.requestFocus();
                    edt_eventDetail.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strEventDetails.matches("[a-zA-Z ]+"))
                {
                    edt_eventDetail.requestFocus();
                    edt_eventDetail.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else{
                    Toast.makeText(EventUpdateActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();
                    apiCall(strEventId, strEventDate, strEventEndDate, strEventDetails, strRent);

                }


            }
        });


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
                        Log.e("year: ", String.valueOf(year));
                        Log.e("month: ", String.valueOf(month));
                        Log.e("day: ", String.valueOf(dayOfMonth));

                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvDate.setText(strDate);
                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvEndDate.setText(strDate);

                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });


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


    private void apiCall(String strEventID, String strEventDate, String strEventEndDate, String strEventDetails, String strRent) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.EVENT_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(EventUpdateActivity.this, EventDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                Log.e("id in update map:", strEventID);
                hashMap.put("eventId", strEventID);
                hashMap.put("eventDate", strEventDate);
                hashMap.put("eventEndDate", strEventEndDate);
                hashMap.put("eventDetails", strEventDetails);
                hashMap.put("rent", strRent);

                return hashMap;


            }
        };
        VolleySingleton.getInstance(EventUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}
