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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.R;
import com.e_society.display.UserDisplayActivity;
import com.e_society.model.UserLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserUpdateActivity extends AppCompatActivity {
    EditText  edtFirstName, edtLastName, edtAge, edtGender, edtContactNo, edtEmail, edtPassword;
    TextView tvDateOfBirth;

    Button btnUser, btnDeleteUser;

    ImageButton btnDate;

    RadioGroup radioGroup;
    RadioButton rmale, rfemale;
    private int date;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent i = getIntent();

        btnUser = findViewById(R.id.btn_add_user);
        edtFirstName = findViewById(R.id.edt_firstName);
        edtLastName = findViewById(R.id.edt_lastName);
        edtAge = findViewById(R.id.edt_age);
        edtContactNo = findViewById(R.id.edt_contactNo);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);

        rmale = findViewById(R.id.u_male);
        rfemale = findViewById(R.id.u_female);
        btnDeleteUser = findViewById(R.id.btn_delete_user);

        radioGroup = findViewById(R.id.radio_grp_Usr);

        tvDateOfBirth = findViewById(R.id.tv_udob);
        btnDate = findViewById(R.id.btn_Bdate);

        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


        String userId = i.getStringExtra("USER_ID");
        String strRoleId = i.getStringExtra("ROLE_ID");
        String strFirstName = i.getStringExtra("FIRST_NAME");
        String strLastName = i.getStringExtra("LAST_NAME");
        String strDob = i.getStringExtra("DATE_OF_BIRTH");
        String strAge = i.getStringExtra("AGE");
        String strGender = i.getStringExtra("GENDER");
        String strContactNo = i.getStringExtra("CONTACT_NO");
        String strEmail = i.getStringExtra("EMAIL");
        String strPassword = i.getStringExtra("PASSWORD");

//        edtRoleId.setText(strRoleId);
        if (strGender.equals("Male")) {
            rmale.setChecked(true);
        } else if (strGender.equals("Female")) {
            rfemale.setChecked(true);
        }

        UserLangModel userLangModel = new UserLangModel();
        edtFirstName.setText(strFirstName);
        edtLastName.setText(strLastName);
        edtAge.setText(strAge);
        edtContactNo.setText(strContactNo);
        edtEmail.setText(strEmail);
        edtPassword.setText(strPassword);
        tvDateOfBirth.setText(strDob);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvDateOfBirth.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        btnUser.setText("Update User");
        btnDeleteUser.setVisibility(View.VISIBLE);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAPI(userId);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strRoleId = edtRoleId.getText().toString();
                String strFirstName = edtFirstName.getText().toString();
                String strLastName = edtLastName.getText().toString();
                String strAge = edtAge.getText().toString();
                String strContactNo = edtContactNo.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();
                String strDate = tvDateOfBirth.getText().toString();

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String strRadioButton = radioButton.getText().toString();

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
                } else if (strDob.length() == 0) {
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
                } else if (strPassword.length() == 0) {
                    edtPassword.requestFocus();
                    edtPassword.setError("FIELD CANNOT BE EMPTY");
                }
                else {
                    Toast.makeText(UserUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    Log.e(strFirstName+" "+strLastName+" "+strDate+" "+strAge,"");
                    Log.e(strContactNo+" "+strEmail+" "+strPassword+" "+strRadioButton,"");
                    apiCall(userId, strRoleId, strFirstName, strLastName, strDate, strAge, strContactNo, strEmail, strPassword, strRadioButton);
                }
            }
        });
    }

    private void apiCall(String userId, String strRoleId, String strFirstName, String strLastName, String strDob, String strAge, String strContactNo, String strEmail, String strPassword, String strRadioButton) {

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api calling done", response);
                Log.e("id in api: ", userId);
                Intent intent = new Intent(UserUpdateActivity.this, UserDisplayActivity.class);
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
                hashMap.put("userId", userId);
                hashMap.put("roleId",strRoleId);
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
        VolleySingleton.getInstance(UserUpdateActivity.this).addToRequestQueue(stringRequest);


    }

    private void deleteAPI(String userId) {

        Log.e("TAG****", "deleteAPI Update " + userId);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.SIGNUP_URL + "/" + userId, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(UserUpdateActivity.this, UserDisplayActivity.class);
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
                hashMap.put("userId", userId);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(UserUpdateActivity.this).addToRequestQueue(stringRequest);


    }

}
