package com.e_society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.adapter.MaintenanceMasterListAdapter;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.display.MaintenanceMasterDisplayActivity;
import com.e_society.model.HouseLangModel;
import com.e_society.model.MaintenanceMasterLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MaintenanceActivity extends AppCompatActivity {

    TextView tvMaintenanceAmount, tvPenalty;
    Button btnMaintenance;
    String strMaintenanceMonth;

    String strMaintenanceAmount,strPenalty;

    String strSelectedHouse,houseId;
    Spinner spinnerHouse;

    CheckBox cbLate;

    Spinner spinnerMonth;
    String strMonths[] = {"Select a Month", "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    TextView tvDisDate, tvPayDate, tvLastDate;
    ImageButton btnDate, btnPayDate, btnLastDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintanance);

        tvMaintenanceAmount = findViewById(R.id.tv_amt);
        tvPenalty = findViewById(R.id.tv_penalty);
        btnMaintenance = findViewById(R.id.btn_maintenance);
        cbLate=findViewById(R.id.cb_late);

        //spinner variable
        spinnerMonth = findViewById(R.id.spinner_month);

        spinnerHouse=findViewById(R.id.spinner_house);
        DisplayHouseApi();


        //date variables
        tvDisDate = findViewById(R.id.tv_create);
        tvPayDate = findViewById(R.id.tv_payDate);
        tvLastDate = findViewById(R.id.tv_lastDate);

        btnDate = findViewById(R.id.btn_date);
        btnPayDate = findViewById(R.id.btn_payDate);
        btnLastDate = findViewById(R.id.btn_lastDate);


        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);




        getMasterApi();

        cbLate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton btn, boolean b) {
                if(btn.isChecked())
                {
                    tvPenalty.setVisibility(View.VISIBLE);
                }
                if(!(btn.isChecked()))
                {
                    strPenalty="0";
                    tvPenalty.setVisibility(View.GONE);

                }
            }
        });

        //Normal code
        btnMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCreateDate = tvDisDate.getText().toString();
                String strPaymentDate = tvPayDate.getText().toString();
                String strLastDate = tvLastDate.getText().toString();

                if (strCreateDate.length() == 0) {
                    tvDisDate.requestFocus();
                    tvDisDate.setError("FIELD CANNOT BE EMPTY");
                } else if (strPaymentDate.length() == 0) {
                    tvPayDate.requestFocus();
                    tvPayDate.setError("FIELD CANNOT BE EMPTY");
                } else if (strLastDate.length() == 0) {
                    tvLastDate.requestFocus();
                    tvLastDate.setError("FIELD CANNOT BE EMPTY");
                }else {
                    if(!(cbLate.isChecked()))
                    {
                        strPenalty="0";
                    }
                    Toast.makeText(MaintenanceActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();

                    apiCall(houseId, strCreateDate, strMaintenanceMonth, strMaintenanceAmount, strPaymentDate, strLastDate, strPenalty);
                }
            }
        });

        //Spinner for Months
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strMonths) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView,
                                                @NonNull ViewGroup parent) {

                        TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);
                        tvData.setTextColor(Color.BLACK);
                        tvData.setTextSize(20);
                        return tvData;
                    }

                };
        spinnerMonth.setAdapter(arrayAdapter);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strMaintenanceMonth = strMonths[position];
                Log.e("month:",strMaintenanceMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Date :- creationDate,paymentDate,lastDate
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy-MM-dd", dtDob);

                        tvDisDate.setText(strDate);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        btnPayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvPayDate.setText(strDate);

                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        btnLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvLastDate.setText(strDate);

                    }
                }, year, month, date);
                datePickerDialog.show();
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

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                        String strUserId = jsonObject2.getString("_id");

                        String strHouseId = jsonObject1.getString("_id");
                        String strHouseDeets = jsonObject1.getString("houseDetails");

                        strHouses[j]=strHouseDeets;
                        j++;
                    }
                    ArrayAdapter<String> arrayAdapter = new
                            ArrayAdapter<String>(MaintenanceActivity.this, android.R.layout.simple_list_item_1, strHouses) {
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

        VolleySingleton.getInstance(MaintenanceActivity.this).addToRequestQueue(stringRequest);

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

    private void getMasterApi() {
        ArrayList<MaintenanceMasterLangModel> arrayList = new ArrayList<MaintenanceMasterLangModel>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.MAINTENANCE_MASTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                         strMaintenanceAmount = jsonObject1.getString("maintenanceAmount");
                         strPenalty=jsonObject1.getString("penalty");

                        tvMaintenanceAmount.setText(strMaintenanceAmount);
                        tvPenalty.setText(strPenalty);
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

        VolleySingleton.getInstance(MaintenanceActivity.this).addToRequestQueue(stringRequest);


    }

    //apicall method
    private void apiCall(String strHouseId, String strCreateDate, String strMaintenanceMonth, String strMaintenanceAmount, String strPaymentDate, String strLastDate, String strPenalty) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.MAINTENANCE_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(MaintenanceActivity.this, MaintenanceDisplayActivity.class);
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
                hashMap.put("creationDate", strCreateDate);
                hashMap.put("month", strMaintenanceMonth);
                hashMap.put("maintenanceAmount", strMaintenanceAmount);
                hashMap.put("paymentDate", strPaymentDate);
                hashMap.put("lastDate", strLastDate);
                hashMap.put("penalty", strPenalty);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(MaintenanceActivity.this).addToRequestQueue(stringRequest);

    }
}