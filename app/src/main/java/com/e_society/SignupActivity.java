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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.e_society.model.RoleLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText edt_fname, edt_lname, edt_age, edt_cn, edt_email, edt_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView tvLogin, tv_forgetpwd;

    RadioGroup radioGender;
    RadioButton rbfemale,rbmale;

    String roleId;

    Button btn_signup;

    TextView tvDob,tvGender;
    ImageButton btnDob;
    private int date;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        edt_fname = findViewById(R.id.fname);
        edt_lname = findViewById(R.id.lname);
        tvDob = findViewById(R.id.tv_dob);
        edt_age = findViewById(R.id.age);

        radioGender = findViewById(R.id.radiogroup);
        rbfemale=findViewById(R.id.female);
        rbmale=findViewById(R.id.male);
        tvGender=findViewById(R.id.tv_gender);

        edt_cn = findViewById(R.id.cn);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.password);
        tv_forgetpwd = findViewById(R.id.forget_pwd);
        tvLogin = findViewById(R.id.tv_login);

        btnDob = findViewById(R.id.btn_dob);
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        Log.e("calling","role api");
        RoleApi();
        Log.e("roleId: ",roleId+"");


        btn_signup = findViewById(R.id.btn_signup);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFname = edt_fname.getText().toString();
                String strLname = edt_lname.getText().toString();
                String strDob = tvDob.getText().toString();
                String strAge = edt_age.getText().toString();


                String strCN = edt_cn.getText().toString();
                String strEmail = edt_email.getText().toString();
                String strPassword = edt_password.getText().toString();


//                if (strFname.equals("")) {
//                    Toast.makeText(SignupActivity.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
//                } else if (strLname.length() < 2) {
//                    Toast.makeText(SignupActivity.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
//                } else if (strDob.length() < 2) {
//                    Toast.makeText(SignupActivity.this, "Please Enter Date Of Birth", Toast.LENGTH_SHORT).show();
//                } else if (strAge.length() < 2) {
//                    Toast.makeText(SignupActivity.this, "Please Enter Age", Toast.LENGTH_SHORT).show();
//                } else if (strCN.length() < 2) {
//                    Toast.makeText(SignupActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
//                } else if (strEmail.equals("")) {
//                    Toast.makeText(SignupActivity.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
//                } else if (!strEmail.matches(emailPattern)) {
//                    Toast.makeText(SignupActivity.this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();
//                } else if (strPassword.isEmpty()) {
//                    Toast.makeText(SignupActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
//                } else if (strPassword.length() < 2) {
//                    Toast.makeText(SignupActivity.this, "PLease Enter Your Valid Password", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SignupActivity.this, "Name is " + strFname, Toast.LENGTH_LONG).show();
//
//                    loadData(strFname, strLname, strDob, strAge, strCN, strEmail, strPassword, strRadioButton);
//                }


                //validation
                if(strFname.length()==0)
                {
                    edt_fname.requestFocus();
                    edt_fname.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strFname.matches("[a-zA-Z ]+"))
                {
                    edt_fname.requestFocus();
                    edt_fname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(strLname.length()==0)
                {
                    edt_lname.requestFocus();
                    edt_lname.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strLname.matches("[a-zA-Z ]+"))
                {
                    edt_lname.requestFocus();
                    edt_lname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(strDob.length()==0)
                {
                    tvDob.requestFocus();
                    tvDob.setError("PLEASE SELECT DATE");
                }
                else if(strAge.length()==0)
                {
                    tvDob.requestFocus();
                    tvDob.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strAge.matches("^[0-9]{1,10}$"))
                {
                    tvDob.requestFocus();
                    tvDob.setError("ENTER ONLY DIGITS");
                }
                else if(!(rbmale.isChecked() || rbfemale.isChecked()))
                {
                    tvGender.requestFocus();
                    tvGender.setError("PLEASE SELECT GENDER");

                }
                else if(strCN.length()==0)
                {
                    edt_cn.requestFocus();
                    edt_cn.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strCN.matches("^[0-9]{10}$"))
                {
                    edt_cn.requestFocus();
                    edt_cn.setError("PLEASE ENTER 10 DIGITS ONLY");
                }
                else if(strEmail.length()==0)
                {
                    edt_email.requestFocus();
                    edt_email.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strEmail.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{0,4}$"))
                {
                    edt_email.requestFocus();
                    edt_email.setError("ENTER A VALID EMAIL ADDRESS");
                }
                else if(strPassword.length()==0)
                {
                    edt_password.requestFocus();
                    edt_password.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
                {
                    edt_password.requestFocus();
                    edt_password.setError("PASSWORD MUST CONTAIN AT LEAST :\n ONE DIGIT, ONE LOWERCASE LETTER, ONE UPPERCASE LETTER,AND A SPECIAL CHARATER\nNO SPACE ALLOWED\nMINIMUM 8 CHARACTERS ALLOWED");
                }
                else {

                    int id = radioGender.getCheckedRadioButtonId();
                    RadioButton rd = findViewById(id);
                    String strRadioButton = rd.getText().toString();

                    Toast.makeText(SignupActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    loadData(roleId,strFname, strLname, strDob, strAge, strCN, strEmail, strPassword, strRadioButton);
                }

            }
        });
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvDob.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

    }

    private void RoleApi() {
        ArrayList<RoleLangModel> arrayList=new ArrayList<RoleLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.ROLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Role Api Calling done","response");
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strRoleId=jsonObject1.getString("_id");
                        String strRoleName = jsonObject1.getString("roleName");

                        if(strRoleName.equalsIgnoreCase("user")) {
                            roleId = jsonObject1.getString(("_id"));
                            Log.e("roleId: ",roleId);
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

        VolleySingleton.getInstance(SignupActivity.this).addToRequestQueue(stringRequest);
    }

    private void loadData(String roleId,String strFname, String strLname, String strDob, String strAge, String strCn, String strEmail, String strPassword, String strRadioButton) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("role",roleId);
                map.put("firstName", strFname);
                map.put("lastName", strLname);
                map.put("dateOfBirth", strDob);
                map.put("age", strAge);
                map.put("gender", strRadioButton);
                map.put("contactNo", strCn);
                map.put("email", strEmail);
                map.put("password", strPassword);


                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}