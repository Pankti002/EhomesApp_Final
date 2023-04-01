package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.e_society.display.FeedbackDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class FeedbackActivity extends AppCompatActivity {

    EditText edt_feedbackHouseId,edt_giveFeedback,edt_acknowledgement;
    TextView tv_feedbackDate;
    ImageButton btn_feedbackDate;
    Button btn_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        edt_feedbackHouseId = findViewById(R.id.edt_feedbackHouseId);
        edt_giveFeedback = findViewById(R.id.edt_giveFeedback);
        edt_acknowledgement = findViewById(R.id.edt_acknowledgement);
        tv_feedbackDate=findViewById(R.id.tv_feedbackDate);
        btn_feedbackDate = findViewById(R.id.btn_feedbackDate);


        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        btn_feedbackDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FeedbackActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tv_feedbackDate.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });




        //add event
        btn_feedback = findViewById(R.id.btn_feedback);


        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // String strFeedbackId = edt_feedbackId.getText().toString();
                String strHouseId = edt_feedbackHouseId.getText().toString();
                String strDate = tv_feedbackDate.getText().toString();
                String strFeedback = edt_giveFeedback.getText().toString();
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
                    Toast.makeText(FeedbackActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    apiCall(strHouseId,strDate,strFeedback,strAcknowledgement);

                }

            }


        });

    }

    private void apiCall( String strHouseId, String strDate, String strFeedback, String strAcknowledgement) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.FEEDBACK_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);

                Intent intent = new Intent(FeedbackActivity.this, FeedbackDisplayActivity.class);

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
                hashMap.put("date", strDate);
                hashMap.put("feedback", strFeedback);
                hashMap.put("acknowledgement",strAcknowledgement);

                return hashMap;

            }
        };
        VolleySingleton.getInstance(FeedbackActivity.this).addToRequestQueue(stringRequest);

    }

}