package com.e_society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.NonMemberDisplayActivity;
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

public class NonMemberActivity extends AppCompatActivity {

    EditText edtVisitorName;
    TextView tvArrivingTime, tvHouseId;
    ImageButton imgBtnArrivingTime;
    RadioGroup radioGroup, pickup_radioGroup, deliver_RadioGroup;
    Button btnAddNonMember;

    String strSelectedHouse,houseId;
    Spinner spinnerNonMember;

    private int hour;
    private int minute;

    TextView tvDate;
    ImageButton btnDate;
    private int date;
    private int month;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_member);

        edtVisitorName = findViewById(R.id.edt_visitorName);
        tvArrivingTime = findViewById(R.id.tv_arrivingTime);
        tvDate=findViewById(R.id.tv_Date);
        imgBtnArrivingTime = findViewById(R.id.btn_arrivingTime);

        spinnerNonMember = findViewById(R.id.spinner_nonMember);

        radioGroup = findViewById(R.id.visited_RadioGroup);
        pickup_radioGroup = findViewById(R.id.pickup_radiogroup);
        deliver_RadioGroup = findViewById(R.id.deliver_radiogroup);

        btnAddNonMember = findViewById(R.id.btn_nonMember);

        btnDate = findViewById(R.id.btn_Date);
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DisplayHouseApi();

        btnAddNonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strVisitorName = edtVisitorName.getText().toString();
                String strArrivingTime = tvArrivingTime.getText().toString();
                String strDate=tvDate.getText().toString();

                if (strVisitorName.length() == 0) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("FIELD CANNOT BE EMPTY");
                } else if (!strVisitorName.matches("[a-zA-Z ]+")) {
                    edtVisitorName.requestFocus();
                    edtVisitorName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strArrivingTime.length() == 0) {
                    tvArrivingTime.requestFocus();
                    tvArrivingTime.setError("FIELD CANNOT BE EMPTY");
                }else  if (strDate.length() == 0) {
                    tvDate.requestFocus();
                    tvDate.setError("FIELD CANNOT BE EMPTY");
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

                    Log.e(strDate,"date");
                    Toast.makeText(NonMemberActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    apiCall( houseId, strVisitorName, strArrivingTime,strDate,strRadioButton,strPRadioButton,strSRadioButton);
                }
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NonMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    private void DisplayHouseApi() {
        ArrayList<HouseLangModel> arrayList = new ArrayList<HouseLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.HOUSE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    int j=1;
                    String strHouses[]=new String[jsonArray.length()+1];
                    strHouses[0]="Select you House";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        strHouses[j]=strHouseDeets;
                        j++;
                    }
                    ArrayAdapter<String> arrayAdapter = new
                            ArrayAdapter<String>(NonMemberActivity.this, android.R.layout.simple_list_item_1, strHouses) {
                                @Override
                                public View getDropDownView(int position, @Nullable View convertView,
                                                            @NonNull ViewGroup parent) {

                                    TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);
                                    tvData.setTextColor(Color.BLACK);
                                    tvData.setTextSize(20);
                                    return tvData;
                                }

                            };
                    spinnerNonMember.setAdapter(arrayAdapter);

                    spinnerNonMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        VolleySingleton.getInstance(NonMemberActivity.this).addToRequestQueue(stringRequest);

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
                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        if(strSelectedHouse.equals(strHouseDeets))
                        {
                            houseId=strHouseId;
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

    private void apiCall( String strHouseId, String strVisitorName, String strArrivingTime , String strDate,String strRadioButton, String strPRadioButton,String strDRadioButton) {
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
                hashMap.put("date",strDate);
                hashMap.put("isVisited", strRadioButton);
                hashMap.put("pickup", strPRadioButton);
                hashMap.put("deliver", strDRadioButton);


                return hashMap;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
