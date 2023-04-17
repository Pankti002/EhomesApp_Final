package com.e_society.update;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.NonMemberActivity;
import com.e_society.R;
import com.e_society.display.EventDisplayActivity;
import com.e_society.display.NonMemberDisplayActivity;
import com.e_society.model.EventLangModel;
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

public class NonMemberUpdateActivity extends AppCompatActivity {


    EditText edtVisitorName;
    TextView tvArrivingTime, tvDate, tv_houseId;
    ImageButton imgBtnArrivingTime;

    String houseId;
    Spinner spinnerHouse;

    ImageButton btnDate;
    private int date;
    private int month;
    private int year;


    RadioGroup radioGroup, pickup_radioGroup, deliver_RadioGroup;
    RadioButton rbVisited, rbNotVisited, rbPick, rbNoPick, rbDeliver,rbNoDeliver;
    Button btnAddNonMember, btnDeleteNonMember;
    private int hour;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member);

        Intent i = getIntent();

        tv_houseId = findViewById(R.id.tv_houseID);
        edtVisitorName = findViewById(R.id.edt_visitorName);
        tvArrivingTime = findViewById(R.id.tv_arrivingTime);
        tvDate=findViewById(R.id.tv_Date);
        imgBtnArrivingTime = findViewById(R.id.btn_arrivingTime);

        spinnerHouse=findViewById(R.id.spinner_nonMember);
        spinnerHouse.setVisibility(View.GONE);


        radioGroup = findViewById(R.id.visited_RadioGroup);
        rbVisited=findViewById(R.id.isVisited);
        rbNotVisited=findViewById(R.id.isNotVisited);

        pickup_radioGroup = findViewById(R.id.pickup_radiogroup);
       rbPick=findViewById(R.id.pickedUp);
       rbNoPick=findViewById(R.id.notPickedUp);

        deliver_RadioGroup = findViewById(R.id.deliver_radiogroup);
        rbDeliver=findViewById(R.id.delivered);
        rbNoDeliver=findViewById(R.id.notDelivered);


        btnDate = findViewById(R.id.btn_Date);
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        btnDeleteNonMember = findViewById(R.id.btn_delete_non_member);
        btnDeleteNonMember.setVisibility(View.VISIBLE);

        btnAddNonMember = findViewById(R.id.btn_nonMember);
        String strNonMemberId = i.getStringExtra("ID");
        houseId= i.getStringExtra("HOUSE_ID");
        String strName = i.getStringExtra("NAME");
        String strTime = i.getStringExtra("ARRIVING_TIME");
        String strDate=i.getStringExtra("DATE");
        String strVisited = i.getStringExtra("VISITED");
        String strPickUp = i.getStringExtra("PICKUP");
        String strDeliver = i.getStringExtra("DELIVER");

        getHouseApi();

        if (strVisited.equals("Visited")) {
            rbVisited.setChecked(true);
        } else if (strVisited.equals("Not Visited")) {
            rbNotVisited.setChecked(true);
        }

        if (strPickUp.equals("Picked Up")) {
            rbPick.setChecked(true);
        } else if (strPickUp.equals("Not Picked Up")) {
            rbNoPick.setChecked(true);
        }

        if (strDeliver.equals("Delivered")) {
            rbDeliver.setChecked(true);
        } else if (strDeliver.equals("Not Delivered")) {
            rbNoDeliver.setChecked(true);
        }

        //set text
        EventLangModel eventLangModel = new EventLangModel();
        edtVisitorName.setText(strName);
        tvArrivingTime.setText(strTime);
        tvDate.setText(strDate);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NonMemberUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvDate.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });


        imgBtnArrivingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NonMemberUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tvArrivingTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });


        btnAddNonMember.setText("Update NonMember");
        btnAddNonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strName = edtVisitorName.getText().toString();
                String strTime=tvArrivingTime.getText().toString();
                String strDate=tvDate.getText().toString();

                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String strRadioButton = radioButton.getText().toString();

                int id1 = pickup_radioGroup.getCheckedRadioButtonId();
                RadioButton pRadioButton = findViewById(id1);
                String strPRadioButton = pRadioButton.getText().toString();

                int id2 = deliver_RadioGroup.getCheckedRadioButtonId();
                RadioButton sRadioButton = findViewById(id2);
                String strSRadioButton = sRadioButton.getText().toString();

                Log.e("Radio Buttons: ",strRadioButton+" "+strPRadioButton+" "+strSRadioButton);

                if (strName.length() == 0) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strName.matches("[a-zA-Z ]+")) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strTime.length() == 0) {
                    tvArrivingTime.requestFocus();
                    tvArrivingTime.setError("FIELD CANNOT BE EMPTY");
                } else if (strDate.length() == 0) {
                    tvDate.requestFocus();
                    tvDate.setError("FIELD CANNOT BE EMPTY");
                }else {
                    Toast.makeText(NonMemberUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    apiCall(strNonMemberId, houseId, strName, strTime,strDate, strRadioButton, strPRadioButton, strSRadioButton);
                }

            }
        });

        btnDeleteNonMember.setVisibility(View.VISIBLE);
        btnDeleteNonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(strNonMemberId+"","Id On Click");
                deleteAPI(strNonMemberId);
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



    private void apiCall(String strNonMemberId, String strHouseId, String strName, String strTime,String strDate,String strRadioButton, String strPRadioButton, String strSRadioButton) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.NONMEMBER_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(NonMemberUpdateActivity.this, NonMemberDisplayActivity.class);
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
                hashMap.put("nonMemberId", strNonMemberId);
                hashMap.put("house", strHouseId);
                hashMap.put("name", strName);
                hashMap.put("arrivingTime",strTime);
                hashMap.put("date",strDate);
                hashMap.put("isVisited", strRadioButton);
                hashMap.put("pickup", strPRadioButton);
                hashMap.put("deliver", strSRadioButton);

                return hashMap;


            }
        };
        VolleySingleton.getInstance(NonMemberUpdateActivity.this).addToRequestQueue(stringRequest);

    }

    private void deleteAPI(String id) {
        Log.e("TAG****", "deleteAPI Delete " + id);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.NONMEMBER_URL+ "/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(NonMemberUpdateActivity.this, NonMemberDisplayActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ",error+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("_id", id);
                return hashMap;

            }
        };
        VolleySingleton.getInstance(NonMemberUpdateActivity.this).addToRequestQueue(stringRequest);


    }
}
