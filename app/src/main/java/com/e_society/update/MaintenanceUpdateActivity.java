package com.e_society.update;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.e_society.R;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.model.MaintenanceLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;


public class MaintenanceUpdateActivity extends AppCompatActivity {
    EditText edtHouseId;
    TextView tvMaintenanceAmount, tvPenalty;
    Button btnMaintenance, btnDeleteMaintenance;
    String strMaintenanceMonth;

    Spinner spinnerMonth;
    String strMonths[] = {"Select a Month", "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    TextView tvDisDate, tvPayDate, tvLastDate;
    ImageButton btnDate, btnPayDate, btnLastDate;
    private int date;
    private int month;
    private int year;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintanance);

        Intent i = getIntent();
        spinnerMonth = findViewById(R.id.spinner_month);
        edtHouseId = findViewById(R.id.et_houseId);
        tvMaintenanceAmount = findViewById(R.id.tv_amt);
        tvPenalty = findViewById(R.id.tv_penalty);
        tvDisDate = findViewById(R.id.tv_create);
        tvPayDate = findViewById(R.id.tv_payDate);
        tvLastDate = findViewById(R.id.tv_lastDate);
        btnDeleteMaintenance = findViewById(R.id.btn_delete_maintenance);


        //Date :- creationDate,paymentDate,lastDate
        btnDate = findViewById(R.id.btn_date);
        btnPayDate = findViewById(R.id.btn_payDate);
        btnLastDate = findViewById(R.id.btn_lastDate);

        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        btnMaintenance = findViewById(R.id.btn_maintenance);

        String strMaintenanceAmount = i.getStringExtra("MAINTENANCE_AMOUNT");
        String strPenalty = i.getStringExtra("PENALTY");
        String strCreationDate = i.getStringExtra("CREATION_DATE");
        String strSelMonth = i.getStringExtra("MONTH");
        String strPaymentDate = i.getStringExtra("PAYMENT_DATE");
        String strLastDate = i.getStringExtra("LAST_DATE");
        String maintenanceId = i.getStringExtra("MAINTENANCE_ID");
        String maintenanceHouse = i.getStringExtra("MAINTENANCE_HOUSE");

        Log.e(strSelMonth+"","Month***");

        //spinner auto selection
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strMonths);
        int position = adapter.getPosition(strSelMonth);
        spinnerMonth.post(new Runnable() {
            @Override
            public void run() {
                spinnerMonth.setSelection(position);
            }
        });

//        edtHouseId.setText(maintenanceHouse);

        //set text
        MaintenanceLangModel maintenanceLangModel = new MaintenanceLangModel();
        tvMaintenanceAmount.setText(strMaintenanceAmount);
        tvPenalty.setText(strPenalty);
        tvDisDate.setText(strCreationDate);
        tvPayDate.setText(strPaymentDate);
        tvLastDate.setText(strLastDate);

        //normal code
        btnMaintenance.setText("Update Maintenance");
        btnDeleteMaintenance.setVisibility(View.VISIBLE);
        btnDeleteMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAPI(maintenanceId);
            }
        });
        btnMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHouseId = edtHouseId.getText().toString();
                String strMaintenanceAmount = tvMaintenanceAmount.getText().toString();
                String strPenalty = tvPenalty.getText().toString();
                String strCreateDate = tvDisDate.getText().toString();
                String strPaymentDate = tvPayDate.getText().toString();
                String strLastDate = tvLastDate.getText().toString();


                apiCall(maintenanceId, "strHouseId", strMaintenanceMonth, strPenalty, strCreateDate, strPaymentDate, strLastDate, strMaintenanceAmount);

            }
        });

        //Spinner for Months
        spinnerMonth = findViewById(R.id.spinner_month);

        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(this, R.layout.spinner, strMonths) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //date
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        tvDisDate.setText(strDate);
                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });

        btnPayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvPayDate.setText(strDate);

                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });

        btnLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaintenanceUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, month, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

                        tvLastDate.setText(strDate);

                    }
                }, date, month, year);
                datePickerDialog.show();
            }
        });

    }

    // 6336d0a385dc006ba7319c3b
    private void deleteAPI(String id1) {

        Log.e("TAG****", "deleteAPI Update " + id1);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.MAINTENANCE_URL + "/" + id1, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(MaintenanceUpdateActivity.this, MaintenanceDisplayActivity.class);
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
                hashMap.put("maintenanceId", id1);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(MaintenanceUpdateActivity.this).addToRequestQueue(stringRequest);


    }

    private void apiCall(String id, String strHouseId, String strMaintenanceMonth, String strPenalty, String strCreateDate, String strPaymentDate, String strLastDate, String strMaintenanceAmount) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.MAINTENANCE_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(MaintenanceUpdateActivity.this, MaintenanceDisplayActivity.class);
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
                hashMap.put("maintenanceId", id);
                hashMap.put("creationDate", strCreateDate);
                hashMap.put("month", strMaintenanceMonth);
                hashMap.put("maintenanceAmount", strMaintenanceAmount);
                hashMap.put("paymentDate", strPaymentDate);
                hashMap.put("lastDate", strLastDate);
                hashMap.put("penalty", strPenalty);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(MaintenanceUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}
