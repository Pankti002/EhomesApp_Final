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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.RoleDisplayActivity;
import com.e_society.display.UserDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    EditText edtRoleId, edtFirstName, edtLastName, edtAge, edtContactNo, edtEmail, edtPassword;
    Button btnUser;
    ImageButton imgBtnBDate;
    TextView tvDateOfBirth;
    RadioGroup radioGroup;


    private int date;
    private int month;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnUser = findViewById(R.id.btn_add_user);
        edtFirstName = findViewById(R.id.edt_firstName);
        edtLastName = findViewById(R.id.edt_lastName);
        tvDateOfBirth = findViewById(R.id.tv_udob);
        edtAge = findViewById(R.id.edt_age);
        edtContactNo = findViewById(R.id.edt_contactNo);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        imgBtnBDate = findViewById(R.id.btn_Bdate);

        radioGroup = findViewById(R.id.radio_grp_Usr);

        imgBtnBDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strEndDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strEndDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvDateOfBirth.setText(strEndDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRoleId = edtRoleId.getText().toString();
                String strFirstName = edtFirstName.getText().toString();
                String strLastName = edtLastName.getText().toString();
                String strDateOfBirth = tvDateOfBirth.getText().toString();

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String strRadioButton = radioButton.getText().toString();

                String strAge = edtAge.getText().toString();
                String strContactNo = edtContactNo.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strFirstName.length() == 0) {
                    edtFirstName.requestFocus();
                    edtFirstName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strFirstName.matches("[a-zA-Z ]+")) {
                    edtFirstName.requestFocus();
                    edtFirstName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strLastName.length() == 0) {
                    edtLastName.requestFocus();
                    edtLastName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strLastName.matches("[a-zA-Z ]+")) {
                    edtLastName.requestFocus();
                    edtLastName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strDateOfBirth.length() == 0) {
                    tvDateOfBirth.requestFocus();
                    tvDateOfBirth.setError("FIELD CANNOT BE EMPTY");
                } else if (strAge.length() == 0) {
                    edtAge.requestFocus();
                    edtAge.setError("FIELD CANNOT BE EMPTY");
                } else if (!strAge.matches("^[0-9]{1,10}$")) {
                    edtAge.requestFocus();
                    edtAge.setError("ENTER ONLY DIGITS");
                } else if (strContactNo.length() == 0) {
                    edtContactNo.requestFocus();
                    edtContactNo.setError("FIELD CANNOT BE EMPTY");
                } else if (!strContactNo.matches("^[0-9]{10}$")) {
                    edtContactNo.requestFocus();
                    edtContactNo.setError("PLEASE ENTER 10 DIGITS ONLY");
                } else if (strEmail.length() == 0) {
                    edtEmail.requestFocus();
                    edtEmail.setError("FIELD CANNOT BE EMPTY");
                } else if (!strEmail.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{0,4}$")) {
                    edtEmail.requestFocus();
                    edtEmail.setError("ENTER A VALID EMAIL ADDRESS");
//                } else if (strPassword.length() == 0) {
//                    edtPassword.requestFocus();
//                    edtPassword.setError("FIELD CANNOT BE EMPTY");
//                } else if (!strPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
//                    edtPassword.requestFocus();
//                    edtPassword.setError("PASSWORD MUST CONTAIN AT LEAST :\n ONE DIGIT, ONE LOWERCASE LETTER, ONE UPPERCASE LETTER,AND A SPECIAL CHARATER\nNO SPACE ALLOWED\nMINIMUM 8 CHARACTERS ALLOWED");
                } else {
                    Toast.makeText(UserActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    userApiCall(strRoleId, strFirstName, strLastName, strDateOfBirth, strAge, strRadioButton, strContactNo, strEmail, strPassword);
                }

            }
        });
    }

    private void userApiCall(String strRoleId, String strFirstName, String strLastName, String
            strDob, String strAge, String strRadioButton, String strContactNo, String strEmail, String
                                     strPassword) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("roleId in add:", strRoleId);
                Log.e("Api calling done!", response);
                Intent i = new Intent(UserActivity.this, UserDisplayActivity.class);
                startActivity(i);
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
                hashMap.put("role", strRoleId);
                hashMap.put("firstName", strFirstName);
                hashMap.put("lastName", strLastName);
                hashMap.put("dateOfBirth", strDob);
                hashMap.put("age", strAge);
                hashMap.put("gender", strRadioButton);
                hashMap.put("contactNo", strContactNo);
                hashMap.put("email", strEmail);
                hashMap.put("password", strPassword);

                return hashMap;
            }
        };
        VolleySingleton.getInstance(UserActivity.this).addToRequestQueue(stringRequest);


    }
}