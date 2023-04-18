package com.e_society;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.MemberDisplayActivity;
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

public class MemberActivity extends AppCompatActivity {
    EditText edtMemberName, edtAge, edtContactNo;
    TextView tv_dateOfBirth, tvGender;

    ImageButton btnDate;
    Button addMember;

    String strSelectedHouse,houseId ;
    Spinner spinnerHouse;

    RadioGroup radioGroup;
    RadioButton rbMale,rbFemale;

    private int date;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        edtMemberName = findViewById(R.id.edt_memberName);
        edtAge = findViewById(R.id.edt_age);

        tv_dateOfBirth = findViewById(R.id.tv_dateOfBirth);
        tvGender = findViewById(R.id.tv_gender);
        edtContactNo = findViewById(R.id.edt_contactNo);
        addMember = findViewById(R.id.btn_member);
        btnDate = findViewById(R.id.btn_memberDate);
        spinnerHouse = findViewById(R.id.spinner_member);

        radioGroup=findViewById(R.id.radio_grp);
        rbMale=findViewById(R.id.radio_male);
        rbFemale=findViewById(R.id.radio_female);

        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        HouseApi();
        //date
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        tv_dateOfBirth.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();

            }
        });

        //add button
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memberName = edtMemberName.getText().toString();
                String age = edtAge.getText().toString();
                String contactNumber = edtContactNo.getText().toString();
                String dateOfBirth = tv_dateOfBirth.getText().toString();


                if(memberName.length()==0)
                {
                    edtMemberName.requestFocus();
                    edtMemberName.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!memberName.matches("[a-zA-Z ]+"))
                {
                    edtMemberName.requestFocus();
                    edtMemberName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(dateOfBirth.length()==0)
                {
                    tv_dateOfBirth.requestFocus();
                    tv_dateOfBirth.setError("FIELD CANNOT BE EMPTY");
                }
                else if(age.length()==0)
                {
                    edtAge.requestFocus();
                    edtAge.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!age.matches("^[0-9]{1,}$"))
                {
                    edtAge.requestFocus();
                    edtAge.setError("ENTER ONLY DIGITS");
                }
                else if(!(rbMale.isChecked() || rbFemale.isChecked()))
                {
                    tvGender.requestFocus();
                    tvGender.setError("PLEASE SELECT GENDER");

                }
                else if(contactNumber.length()==0)
                {
                    edtContactNo.requestFocus();
                    edtContactNo.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!contactNumber.matches("^[0-9]{10}$"))
                {
                    edtContactNo.requestFocus();
                    edtContactNo.setError("ENTER ONLY DIGITS");
                }
                else
                {

                    int id = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(id);
                    String strRadioButton = radioButton.getText().toString();

                    Log.e(houseId+"","House Id");

                    Toast.makeText(MemberActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    apiCall(houseId, memberName,strRadioButton, age, contactNumber, dateOfBirth);
                }
            }
        });
    }

    private void HouseApi() {
        ArrayList<HouseLangModel> arrayList = new ArrayList<HouseLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.HOUSE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    int j=1;
                    String[] strHouses = new String[jsonArray.length()+1];
                    strHouses[0]="Select Your house";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        strHouses[j]=strHouseDeets;
                        j++;


                    }
                    ArrayAdapter<String> arrayAdapter = new
                            ArrayAdapter<String>(MemberActivity.this, android.R.layout.simple_list_item_1, strHouses) {
                                @Override
                                public View getDropDownView(int position, @Nullable View convertView,
                                                            @NonNull ViewGroup parent) {

                                    TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);
                                    tvData.setTextColor(Color.BLACK);
                                    tvData.setTextSize(20);
                                    return tvData;
                                }

                            };
                    spinnerHouse.setAdapter(arrayAdapter);

                    spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            strSelectedHouse = strHouses[position];
                            Log.e("selected user",strSelectedHouse);
                            getHouseId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



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

    private void getHouseId() {
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

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                        String strUserId = jsonObject2.getString("_id");

                        Log.e(strUserId+"user","UserId");

                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");


                        if(strHouseDeets.equals(strSelectedHouse))
                        {
                            Log.e("in get api",strSelectedHouse);
                            houseId=strHouseId;
                            Log.e(houseId,"House id in spinner");
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


    private void apiCall(String strHouseId, String strMemberName,String strGender, String strAge, String strContactNumber, String strDateOfBirt) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.MEMBER_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);

                Intent intent = new Intent(MemberActivity.this, MemberDisplayActivity.class);

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
                hashMap.put("houseId", strHouseId);
                hashMap.put("memberName", strMemberName);
                hashMap.put("gender", strGender);
                hashMap.put("age", strAge);
                hashMap.put("contactNo", strContactNumber);
                hashMap.put("dateOfBirth", strDateOfBirt);

                return hashMap;

            }
        };
        VolleySingleton.getInstance(MemberActivity.this).addToRequestQueue(stringRequest);

    }
}