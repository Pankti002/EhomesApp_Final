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
import com.e_society.MemberActivity;
import com.e_society.R;
import com.e_society.display.MemberDisplayActivity;
import com.e_society.model.MemberLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class MemberUpdateActivity extends AppCompatActivity {

    EditText edt_houseId, edt_memberName, edt_age, edt_contactNo;
    TextView tv_dateOfBirth,tv_gender;
    ImageButton btn_memberDate;
    Button btn_member, btn_delete;

    RadioGroup radioGroup;
    RadioButton rbMale,rbFemale;


    private int date;
    private int month;
    private int year;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        Intent i = getIntent();

        edt_houseId = findViewById(R.id.edt_houseId);
        edt_memberName = findViewById(R.id.edt_memberName);
        edt_age = findViewById(R.id.edt_age);
        edt_contactNo = findViewById(R.id.edt_contactNo);
        tv_dateOfBirth = findViewById(R.id.tv_dateOfBirth);
        tv_gender=findViewById(R.id.tv_gender);
        btn_memberDate = findViewById(R.id.btn_memberDate);
        btn_member = findViewById(R.id.btn_member);
        btn_delete = findViewById(R.id.btn_delete_member);

        radioGroup = findViewById(R.id.radio_grp);
        rbMale=findViewById(R.id.radio_male);
        rbFemale=findViewById(R.id.radio_female);


        //    Log.e("MAINTENANCE_ID", String.valueOf(maintenanceId));
        String strMemberId = i.getStringExtra("MEMBER_ID");
        String strHouseId = i.getStringExtra("HOUSE_ID");
        String strMemberName = i.getStringExtra("MEMBER_NAME");
        String strAge = i.getStringExtra("AGE");
        String strGender=i.getStringExtra("GENDER");
        String strContactNo = i.getStringExtra("CONTACT_NUMBER");
        String strDateOfBirth = i.getStringExtra("DATE_OF_BIRTH");


        if (strGender.equals("Male")) {
            rbMale.setChecked(true);
        } else if (strGender.equals("Female")) {
            rbFemale.setChecked(true);
        }

        //set text
        MemberLangModel memberLangModel = new MemberLangModel();
        edt_houseId.setText(strHouseId);
        edt_memberName.setText(strMemberName);
        tv_dateOfBirth.setText(strDateOfBirth);
        edt_age.setText(strAge);
        edt_contactNo.setText(strContactNo);


        btn_member.setText("Update Member");
        btn_delete.setVisibility(View.VISIBLE);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMember(strMemberId);
            }
        });
        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String strHouseId = edt_houseId.getText().toString();
                String strMemberName = edt_memberName.getText().toString();
                String strAge = edt_age.getText().toString();
                String strContactNo = edt_contactNo.getText().toString();
                String strDateOfBirth = tv_dateOfBirth.getText().toString();

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String strRadioButton = radioButton.getText().toString();


                if(strMemberName.length()==0)
                {
                    edt_memberName.requestFocus();
                    edt_memberName.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strMemberName.matches("[a-zA-Z ]+"))
                {
                    edt_memberName.requestFocus();
                    edt_memberName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(strDateOfBirth.length()==0)
                {
                    tv_dateOfBirth.requestFocus();
                    tv_dateOfBirth.setError("FIELD CANNOT BE EMPTY");
                }
                else if(strAge.length()==0)
                {
                    edt_age.requestFocus();
                    edt_age.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strAge.matches("^[0-9]{1,}$"))
                {
                    edt_age.requestFocus();
                    edt_age.setError("ENTER ONLY DIGITS");
                }
                else if(strContactNo.length()==0)
                {
                    edt_contactNo.requestFocus();
                    edt_contactNo.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strContactNo.matches("^[0-9]{10}$"))
                {
                    edt_contactNo.requestFocus();
                    edt_contactNo.setError("ENTER ONLY DIGITS");
                }
                else if(!(rbMale.isChecked() || rbFemale.isChecked()))
                {
                    tv_gender.requestFocus();
                    tv_gender.setError("PLEASE SELECT GENDER");

                }
                else
                {
                    Toast.makeText(MemberUpdateActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    apiCall(strMemberId, strHouseId, strMemberName, strAge, strContactNo, strDateOfBirth, strRadioButton);

                }



            }
        });


        //date
        btn_memberDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MemberUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tv_dateOfBirth.setText(strDate);
                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });


    }

    private void deleteMember(String id1) {

        Log.e("TAG****", "deleteAPI Update " + id1);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.MEMBER_URL + "/" + id1, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(MemberUpdateActivity.this, MemberDisplayActivity.class);
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
                hashMap.put("memberId", id1);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(MemberUpdateActivity.this).addToRequestQueue(stringRequest);


    }

    private void apiCall(String strMemberId, String strHouseId, String strMemberName, String strAge, String strDateOfBirth, String strContactNo, String strRadioButton) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.MEMBER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Update api calling done", response+" "+strMemberName);
                Intent intent = new Intent(MemberUpdateActivity.this, MemberDisplayActivity.class);
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
                hashMap.put("memberId", strMemberId);
                hashMap.put("houseId", strHouseId);
                hashMap.put("memberName", strMemberName);
                hashMap.put("age", strAge);
                hashMap.put("dateOfBirth", strDateOfBirth);
                hashMap.put("contactNo", strContactNo);
                hashMap.put("gender", strRadioButton);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(MemberUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}