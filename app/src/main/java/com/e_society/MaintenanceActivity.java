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
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MaintenanceActivity extends AppCompatActivity {

    EditText edtHouseId, edtMaintenanceAmount, edtPenalty;
    Button btnMaintenance;
    String strMaintenanceMonth;

//    RadioGroup radioGroup;
    Spinner spinnerMonth;
    String strMonths[] = {"Select a Month", "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    TextView tvDisDate, tvPayDate, tvLastDate;
    ImageButton btnDate, btnPayDate, btnLastDate;
    private int date;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintanance);

        edtHouseId = findViewById(R.id.et_houseId);
        edtMaintenanceAmount = findViewById(R.id.et_amt);
        edtPenalty = findViewById(R.id.et_penalty);
        btnMaintenance = findViewById(R.id.btn_maintenance);

        //spinner variable
        spinnerMonth = findViewById(R.id.spinner_month);

//        //radio button
//        radioGroup = findViewById(R.id.radio_grp);

        //date variables
        tvDisDate = findViewById(R.id.tv_create);
        tvPayDate = findViewById(R.id.tv_payDate);
        tvLastDate = findViewById(R.id.tv_lastDate);

        btnDate = findViewById(R.id.btn_date);
        btnPayDate = findViewById(R.id.btn_payDate);
        btnLastDate = findViewById(R.id.btn_lastDate);

        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        //Normal code
        btnMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHouseId=edtHouseId.getText().toString();
                String strMaintenanceAmount = edtMaintenanceAmount.getText().toString();
                String strPenalty = edtPenalty.getText().toString();
                String strCreateDate = tvDisDate.getText().toString();
                String strPaymentDate = tvPayDate.getText().toString();
                String strLastDate = tvLastDate.getText().toString();
//                int id = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = findViewById(id);
//
//                String strRadioButton = radioButton.getText().toString();
                if (strCreateDate.length() == 0) {
                    tvDisDate.requestFocus();
                    tvDisDate.setError("FIELD CANNOT BE EMPTY");
                } else if (strMaintenanceAmount.length() == 0) {
                    edtMaintenanceAmount.requestFocus();
                    edtMaintenanceAmount.setError("FIELD CANNOT BE EMPTY");
                } else if (!strMaintenanceAmount.matches("^[0-9]{1,10}$")) {
                    edtMaintenanceAmount.requestFocus();
                    edtMaintenanceAmount.setError("ENTER ONLY DIGITS");
                } else if (strPaymentDate.length() == 0) {
                    tvPayDate.requestFocus();
                    tvPayDate.setError("FIELD CANNOT BE EMPTY");
                } else if (strLastDate.length() == 0) {
                    tvLastDate.requestFocus();
                    tvLastDate.setError("FIELD CANNOT BE EMPTY");
                }else if (strPenalty.length() ==0) {
                    edtPenalty.requestFocus();
                    edtPenalty.setError("FIELD CANNOT BE EMPTY");
                } else if (!strPenalty.matches("^[0-9]{1,10}$")) {
                    edtPenalty.requestFocus();
                    edtPenalty.setError("ENTER ONLY DIGITS");
                } else {
                    Toast.makeText(MaintenanceActivity.this, "Validation Successful", Toast.LENGTH_SHORT).show();
                    apiCall(strHouseId, strCreateDate, strMaintenanceMonth, strMaintenanceAmount, strPaymentDate, strLastDate, strPenalty);
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
                        tvData.setTextColor(Color.WHITE);
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
                        Log.e("year: ", String.valueOf(year));
                        Log.e("month: ", String.valueOf(month));
                        Log.e("day: ", String.valueOf(dayOfMonth));

                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("yyyy/MM/dd", dtDob);

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
//                hashMap.put("maintenancePaid", strRadioButton);
                hashMap.put("paymentDate", strPaymentDate);
                hashMap.put("lastDate", strLastDate);
                hashMap.put("penalty", strPenalty);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(MaintenanceActivity.this).addToRequestQueue(stringRequest);

    }
}