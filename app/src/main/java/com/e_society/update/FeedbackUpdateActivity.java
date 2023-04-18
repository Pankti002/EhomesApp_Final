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
import android.widget.Spinner;
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
import com.e_society.model.HouseLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class FeedbackUpdateActivity extends AppCompatActivity {


    EditText edt_giveFeedback,edt_acknowledgement;
    TextView tv_feedbackDate,tv_houseId;
    ImageButton btn_feedbackDate;
    Button btn_feedback, btn_delFeedback;

    String houseId;
    Spinner spinnerHouse;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent i = getIntent();

        tv_houseId = findViewById(R.id.edt_feedbackHouseId);
        edt_giveFeedback= findViewById(R.id.edt_giveFeedback);
        edt_acknowledgement = findViewById(R.id.edt_acknowledgement);
        tv_feedbackDate = findViewById(R.id.tv_feedbackDate);
        btn_feedbackDate= findViewById(R.id.btn_feedbackDate);
        btn_feedback = findViewById(R.id.btn_feedback);
        btn_delFeedback=findViewById(R.id.btn_delFeedback);

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        spinnerHouse=findViewById(R.id.spinner_house);
        spinnerHouse.setVisibility(View.GONE);


        //    Log.e("MAINTENANCE_ID", String.valueOf(maintenanceId));
        String strFeedbackId = i.getStringExtra("FEEDBACK_ID");
        houseId = i.getStringExtra("HOUSE_ID");
        String strFeedback = i.getStringExtra("FEEDBACK");
        String strDate = i.getStringExtra("DATE");
        String strAcknowledgement = i.getStringExtra("ACKNOWLEDGEMENT");

        getHouseApi();




        //set text
        FeedbackLangModel feedbackLangModel = new FeedbackLangModel();
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
                    apiCall(strFeedbackId,houseId,strFeedback,strDate,strAcknowledgement );
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

                        strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        tv_feedbackDate.setText(strDate);
                    }
                }, year,month,date);
                datePickerDialog.show();
            }
        });

    }

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
                            tv_houseId.setText(strHouseDeets);
                            tv_houseId.setVisibility(View.VISIBLE);
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
