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
import com.e_society.display.EventDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    EditText edtEventDetails, edtRent, edtPlaceId, edtHouseId;
    Button btnAddEvent;
    TextView tvDate, tvEndDate;
    ImageButton btnDate, btnEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        tvDate = findViewById(R.id.tv_date);
        tvEndDate = findViewById(R.id.tv_eventEndDate);

        edtEventDetails = findViewById(R.id.edt_eventDetail);
        edtRent = findViewById(R.id.edt_rent);
        edtHouseId = findViewById(R.id.edt_eHouseId);
        edtPlaceId = findViewById(R.id.edt_ePlaceId);


        //date
        btnDate = findViewById(R.id.btn_date);
        btnEndDate = findViewById(R.id.btn_endDate);
        tvDate = findViewById(R.id.tv_date);
        tvEndDate = findViewById(R.id.tv_eventEndDate);

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
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


                String strHouseId = edtHouseId.getText().toString();
                String strPlaceId = edtPlaceId.getText().toString();
                String strDate = tvDate.getText().toString();
                String strEndDate = tvEndDate.getText().toString();
                String strEventDetails = edtEventDetails.getText().toString();
                String strRent = edtRent.getText().toString();

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
                    edtRent.requestFocus();
                    edtRent.setError("FIELD CANNOT BE EMPTY");
                }
                else if (!strRent.matches("^[0-9]{1,10}$")) {
                    edtRent.requestFocus();
                    edtRent.setError("ENTER ONLY DIGITS");
                }
                else{
                    Toast.makeText(EventActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();
                    apiCall(strHouseId, strPlaceId, strDate, strEndDate, strEventDetails, strRent);

                }

            }


        });

    }

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










