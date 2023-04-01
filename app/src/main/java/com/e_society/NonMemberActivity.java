package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.NonMemberDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class NonMemberActivity extends AppCompatActivity {

    EditText edtHouseId, edtVisitorName;
    TextView tvArrivingTime;
    ImageButton imgBtnArrivingTime;
    RadioGroup radioGroup, pickup_radioGroup, deliver_RadioGroup;
    Button btnAddNonMember;
    private int hour;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member);

        edtHouseId = findViewById(R.id.edt_houseId);
        edtVisitorName = findViewById(R.id.edt_visitorName);
        tvArrivingTime = findViewById(R.id.tv_arrivingTime);
        imgBtnArrivingTime = findViewById(R.id.btn_arrivingTime);

        radioGroup = findViewById(R.id.visited_RadioGroup);
        pickup_radioGroup = findViewById(R.id.pickup_radiogroup);
        deliver_RadioGroup = findViewById(R.id.deliver_radiogroup);

        btnAddNonMember = findViewById(R.id.btn_nonMember);

        btnAddNonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHouseId = edtHouseId.getText().toString();
                String strVisitorName = edtVisitorName.getText().toString();
                String strArrivingTime = tvArrivingTime.getText().toString();


                if (strVisitorName.length() == 0) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strVisitorName.matches("[a-zA-Z ]+")) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strArrivingTime.length() == 0) {
                    tvArrivingTime.requestFocus();
                    tvArrivingTime.setError("FIELD CANNOT BE EMPTY");
                } else {

                    int id = radioGroup.getCheckedRadioButtonId();
                    int pid = pickup_radioGroup.getCheckedRadioButtonId();
                    int sid = deliver_RadioGroup.getCheckedRadioButtonId();

                    RadioButton radioButton = findViewById(id);
                    RadioButton pRadioButton = findViewById(pid);
                    RadioButton sRadioButton = findViewById(sid);

                    String strRadioButton = radioButton.getText().toString();
                    String strPRadioButton = pRadioButton.getText().toString();
                    String strSRadioButton = sRadioButton.getText().toString();

                    Log.e("radioButtons: ",strRadioButton+" "+strPRadioButton+" "+strSRadioButton);

                    Toast.makeText(NonMemberActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    apiCall( strHouseId, strVisitorName, strArrivingTime,strRadioButton,strPRadioButton,strSRadioButton);
                }
            }
        });
        imgBtnArrivingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NonMemberActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tvArrivingTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });
    }

    private void apiCall( String strHouseId, String strVisitorName, String strArrivingTime,String strRadioButton, String strPRadioButton,String strDRadioButton) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.NONMEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Place Response ===", "onResponse: " + response);
                Intent i = new Intent(NonMemberActivity.this, NonMemberDisplayActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("house", strHouseId);
                hashMap.put("name", strVisitorName);
                hashMap.put("arrivingTime", strArrivingTime);
                hashMap.put("isVisited", strRadioButton);
                hashMap.put("pickup", strPRadioButton);
                hashMap.put("deliver", strDRadioButton);


                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
