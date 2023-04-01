package com.e_society.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.HouseActivity;
import com.e_society.R;
import com.e_society.display.HouseDisplayActivity;
import com.e_society.display.MaintenanceDisplayActivity;
import com.e_society.model.HouseLangModel;
import com.e_society.model.UserLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HouseUpdateActivity extends AppCompatActivity {

    EditText edtDeets;
    Button btnAddHouse, btnDelete;

    String strUsers[]=new String[3];
    Spinner spinnerUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        Intent i = getIntent();

        edtDeets = findViewById(R.id.edt_House_Deets);
        btnAddHouse = findViewById(R.id.btn_addHouse);
        btnDelete = findViewById(R.id.btn_delete_House);

        spinnerUsers=findViewById(R.id.spinner_users);
        strUsers[0]="Select Your Name";
        DisplayUserApi();

        String strHouseDeets = i.getStringExtra("HOUSE_DEETS");
        String HouseId = i.getStringExtra("HOUSE_ID");


        HouseLangModel houseLangModel = new HouseLangModel();
        edtDeets.setText(strHouseDeets);
        Log.e(strHouseDeets, strHouseDeets);
        btnAddHouse.setText("Update House");
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAPI(HouseId);
            }
        });


        btnAddHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strHouseDeets = edtDeets.getText().toString();
                if(strHouseDeets.length()==0)
                {
                    edtDeets.requestFocus();
                    edtDeets.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strHouseDeets.matches("[a-zA-Z ]+"))
                {
                    edtDeets.requestFocus();
                    edtDeets.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else {
                    Toast.makeText(HouseUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    apiCall(HouseId, strHouseDeets);
                }

            }
        });

    }

    private void DisplayUserApi() {
            ArrayList<UserLangModel> arrayList = new ArrayList<UserLangModel>();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SIGNUP_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("in Display Users", "Display--onResponse:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        int j=1;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            JSONObject role = jsonObject1.getJSONObject("role");
                            String roleId = role.getString("_id");
                            String strUserId = jsonObject1.getString("_id");
                            String strFirstName = jsonObject1.getString("firstName");


//                        users.add(j,strFirstName);
                            strUsers[j]=strFirstName;
                            j++;

                            String strLastName = jsonObject1.getString("lastName");
                            String strDateOfBirth = jsonObject1.getString("dateOfBirth");
                            String strAge = jsonObject1.getString("age");
                            String strGender = jsonObject1.getString("gender");
                            String strContactNo = jsonObject1.getString("contactNo");
                            String strEmail = jsonObject1.getString("email");
                            String strPassword = jsonObject1.getString("password");
                            Log.e(jsonArray.length()+"","Length");


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error: ", String.valueOf(error));
                }
            });
            VolleySingleton.getInstance(HouseUpdateActivity.this).addToRequestQueue(stringRequest);
    }

    private void deleteAPI(String houseId) {
        Log.e("TAG****", "deleteAPI Update " + houseId);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.HOUSE_URL + "/" + houseId, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(HouseUpdateActivity.this, HouseDisplayActivity.class);
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
                hashMap.put("houseId", houseId);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(HouseUpdateActivity.this).addToRequestQueue(stringRequest);


    }


    private void apiCall(String houseId, String strHouseDeets) {


        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.HOUSE_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", houseId + strHouseDeets);
                Intent intent = new Intent(HouseUpdateActivity.this, HouseDisplayActivity.class);
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
                hashMap.put("houseId", houseId);
                hashMap.put("houseDetails", strHouseDeets);

                return hashMap;

            }
        };
        VolleySingleton.getInstance(HouseUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}