package com.e_society.update;


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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.R;
import com.e_society.display.FeedbackDisplayActivity;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.model.FeedbackLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;
public class FeedbackUpdateActivity extends AppCompatActivity {


    EditText edt_feedbackHouseId,edt_giveFeedback,edt_acknowledgement;
    TextView tv_feedbackDate;
    ImageButton btn_feedbackDate;
    Button btn_feedback, btn_delFeedback;

    private int date;
    private int month;
    private int year;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent i = getIntent();

        edt_feedbackHouseId = findViewById(R.id.edt_feedbackHouseId);
        edt_giveFeedback= findViewById(R.id.edt_giveFeedback);
        edt_acknowledgement = findViewById(R.id.edt_acknowledgement);
        tv_feedbackDate = findViewById(R.id.tv_feedbackDate);
        btn_feedbackDate= findViewById(R.id.btn_feedbackDate);
        btn_feedback = findViewById(R.id.btn_feedback);
        btn_delFeedback=findViewById(R.id.btn_delFeedback);


        //    Log.e("MAINTENANCE_ID", String.valueOf(maintenanceId));
        String strFeedbackId = i.getStringExtra("FEEDBACK_ID");
        String strHouseId = i.getStringExtra("HOUSE_ID");
        String strFeedback = i.getStringExtra("FEEDBACK");
        String strDate = i.getStringExtra("DATE");
        String strAcknowledgement = i.getStringExtra("ACKNOWLEDGEMENT");




        //set text
        FeedbackLangModel feedbackLangModel = new FeedbackLangModel();
        edt_feedbackHouseId.setText(strHouseId);
        edt_giveFeedback.setText(strFeedback);
        tv_feedbackDate.setText(strDate);
        edt_acknowledgement.setText(strAcknowledgement);

        btn_delFeedback.setVisibility(View.VISIBLE);
        btn_delFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAPI(strFeedbackId);
            }
        });

        btn_feedback.setText("Update Feedback");
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHouseId = edt_feedbackHouseId.getText().toString();
                String strFeedback = edt_giveFeedback.getText().toString();
                String strDate = tv_feedbackDate.getText().toString();
                String strAcknowledgement = edt_acknowledgement.getText().toString();

                if(strDate.length()==0)
                {
                    tv_feedbackDate.requestFocus();
                    tv_feedbackDate.setError("FIELD CANNOT BE EMPTY");
                }
                else if(strFeedback.length()==0)
                {
                    edt_giveFeedback.requestFocus();
                    edt_giveFeedback.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strFeedback.matches("[a-zA-Z ]+"))
                {
                    edt_giveFeedback.requestFocus();
                    edt_giveFeedback.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(strAcknowledgement.length()==0)
                {
                    edt_acknowledgement.requestFocus();
                    edt_acknowledgement.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strAcknowledgement.matches("[a-zA-Z ]+"))
                {
                    edt_acknowledgement.requestFocus();
                    edt_acknowledgement.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else
                {
                    Toast.makeText(FeedbackUpdateActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    apiCall(strFeedbackId,strHouseId,strFeedback,strDate,strAcknowledgement );
                }

            }
        });


        //date
        btn_feedbackDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(FeedbackUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tv_feedbackDate.setText(strDate);
                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });




    }

    private void deleteAPI(String id) {
        Log.e("TAG****", "deleteAPI Update " + id);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.FEEDBACK_URL + "/" + id, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(FeedbackUpdateActivity.this, FeedbackDisplayActivity.class);
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
                hashMap.put("feedbackId", id);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(FeedbackUpdateActivity.this).addToRequestQueue(stringRequest);


    }


    private void apiCall(String strFeedbackId,String strHouseId, String strFeedback ,String strDate , String strAcknowledgement ) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.FEEDBACK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(FeedbackUpdateActivity.this, FeedbackDisplayActivity.class);
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
                hashMap.put("feedbackId", strFeedbackId);
                hashMap.put("houseId" , strHouseId);
                hashMap.put("feedback", strFeedback);
                hashMap.put("date", strDate);
                hashMap.put("acknowledgement", strAcknowledgement);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(FeedbackUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}
