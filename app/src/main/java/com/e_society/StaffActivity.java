package com.e_society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.StaffDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StaffActivity extends AppCompatActivity {

    EditText edtStaffName, edtContact, edtAddress,edtEmail,edtPassword, edtAgencyName, edtAgencyContact;

    Button btnStaff;

    ImageButton btnEntry, btnExit;
    TextView tvEntry, tvExit, tvType;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

//        time
        tvEntry = findViewById(R.id.tv_entry);
        tvExit = findViewById(R.id.tv_exit);
        tvType=findViewById(R.id.tv_type);
        btnEntry = findViewById(R.id.btn_entry);
        btnExit = findViewById(R.id.btn_exit);


        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //entry time
        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(StaffActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tvEntry.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        //exit time
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(StaffActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tvExit.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });


        //normal code
        edtStaffName = findViewById(R.id.et_name);
        edtContact = findViewById(R.id.et_contact);
        edtAddress = findViewById(R.id.et_add);
        edtEmail=findViewById(R.id.et_email);
        edtPassword=findViewById(R.id.et_pwd);
        edtAgencyName = findViewById(R.id.et_agencyName);
        edtAgencyContact = findViewById(R.id.et_agencyContact);

        tvEntry = findViewById(R.id.tv_entry);
        tvExit = findViewById(R.id.tv_exit);

        btnStaff = findViewById(R.id.btn_staff);

        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strStaffName = edtStaffName.getText().toString();
                String strType=tvType.getText().toString();
                Log.e(strType+"","Type");
                String strContact = edtContact.getText().toString();
                String strAddress = edtAddress.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();
                String strAgencyName = edtAgencyName.getText().toString();
                String strAgencyContact = edtAgencyContact.getText().toString();
                String strEntryTime = tvEntry.getText().toString();
                String strExitTime = tvExit.getText().toString();

                if (strStaffName.length() == 0) {
                    edtStaffName.requestFocus();
                    edtStaffName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strStaffName.matches("[a-zA-Z ]+")) {
                    edtStaffName.requestFocus();
                    edtStaffName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strEntryTime.length() == 0) {
                    tvEntry.requestFocus();
                    tvEntry.setError("FIELD CANNOT BE EMPTY");
                } else if (strExitTime.length() == 0) {
                    tvExit.requestFocus();
                    tvExit.setError("FIELD CANNOT BE EMPTY");
                } else if (strContact.length() == 0) {
                    edtContact.requestFocus();
                    edtContact.setError("FIELD CANNOT BE EMPTY");
                } else if (!strContact.matches("^[0-9]{10}$")) {
                    edtContact.requestFocus();
                    edtContact.setError("PLEASE ENTER 10 DIGITS ONLY");
                } else if (strAddress.length() == 0) {
                    edtAddress.requestFocus();
                    edtAddress.setError("FIELD CANNOT BE EMPTY");
                } else if (!strAddress.matches("[a-zA-Z ]+")) {
                    edtAddress.requestFocus();
                    edtAddress.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strEmail.length() == 0) {
                    edtEmail.requestFocus();
                    edtEmail.setError("FIELD CANNOT BE EMPTY");
                } else if (!strEmail.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{0,4}$")) {
                    edtEmail.requestFocus();
                    edtEmail.setError("ENTER A VALID EMAIL ADDRESS");
                } else if (strPassword.length() == 0) {
                    edtPassword.requestFocus();
                    edtPassword.setError("FIELD CANNOT BE EMPTY");
                } else if (!strPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                    edtPassword.requestFocus();
                    edtPassword.setError("PASSWORD MUST CONTAIN AT LEAST :\n ONE DIGIT, ONE LOWERCASE LETTER, ONE UPPERCASE LETTER,AND A SPECIAL CHARATER\nNO SPACE ALLOWED\nMINIMUM 8 CHARACTERS ALLOWED");
                } else if (strAgencyName.length() == 0) {
                    edtAgencyName.requestFocus();
                    edtAgencyName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strAgencyName.matches("[a-zA-Z ]+")) {
                    edtAgencyName.requestFocus();
                    edtAgencyName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strAgencyContact.length() == 0) {
                    edtAgencyContact.requestFocus();
                    edtAgencyContact.setError("FIELD CANNOT BE EMPTY");
                } else if (!strAgencyContact.matches("^[0-9]{10}$")) {
                    edtAgencyContact.requestFocus();
                    edtAgencyContact.setError("PLEASE ENTER 10 DIGITS ONLY");
                } else {
                    Toast.makeText(StaffActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    Log.e("Data: ", strStaffName + " " + strContact + " " + strAddress + " " + strAgencyName + " " + strAgencyContact + " " + strEntryTime + " " + strExitTime + " " + strType);
                    apicall(strStaffName, strType, strEntryTime, strExitTime, strContact, strAddress, strEmail,strPassword,strAgencyName, strAgencyContact);
                }
            }
        });

    }

    private void apicall(String strStaffName, String strType, String strEntryTime, String strExitTime, String strContact, String strAddress,String strEmail,String strPassword, String strAgencyName, String strAgencyContact) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.STAFF_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response: ", response);
                Intent intent = new Intent(StaffActivity.this, StaffDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("staffMemberName", strStaffName);
                hashMap.put("type", strType);
                hashMap.put("entryTime", strEntryTime);
                hashMap.put("exitTime", strExitTime);
                hashMap.put("contactNo", strContact);
                hashMap.put("address", strAddress);
                hashMap.put("email", strEmail);
                hashMap.put("password", strPassword);
                hashMap.put("agencyName", strAgencyName);
                hashMap.put("agencyContactNumber", strAgencyContact);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(StaffActivity.this).addToRequestQueue(stringRequest);

    }
}

