package com.e_society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.adapter.UserListAdapter;
import com.e_society.display.HouseDisplayActivity;
import com.e_society.display.UserDisplayActivity;
import com.e_society.model.UserLangModel;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;
//import com.e_society.display.HouseDisplayActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HouseActivity extends AppCompatActivity {

    EditText edtDeets;
    Button btnAdd;

    String strSelectedUser, userId;
    Spinner spinnerUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        edtDeets = findViewById(R.id.edt_House_Deets);
        btnAdd = findViewById(R.id.btn_addHouse);

        spinnerUsers=findViewById(R.id.spinner_users);

        DisplayUserApi();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strHouseDeets = edtDeets.getText().toString();

                if(strHouseDeets.length()==0)
                {
                    edtDeets.requestFocus();
                    edtDeets.setError("FIELD CANNOT BE EMPTY");
                }else if(!strHouseDeets.matches("[a-zA-Z0-9\\s,.'-]+"))
                {
                    edtDeets.requestFocus();
                    edtDeets.setError("ENTER VALID DETAILS");
                }else {
                    Toast.makeText(HouseActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    houseApi(strHouseDeets,userId);
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
                    String strUsers[]=new String[jsonArray.length()+1];
                    strUsers[0]="Select Your Name";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strUserId = jsonObject1.getString("_id");
                        String strFirstName = jsonObject1.getString("firstName");


                        strUsers[j]=strFirstName;
                        j++;
                    }
                    ArrayAdapter<String> arrayAdapter = new
                            ArrayAdapter<String>(HouseActivity.this, android.R.layout.simple_list_item_1, strUsers) {
                                @Override
                                public View getDropDownView(int position, @Nullable View convertView,
                                                            @NonNull ViewGroup parent) {

                                    TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);
                                    tvData.setTextColor(Color.BLACK);
                                    tvData.setTextSize(20);
                                    return tvData;
                                }

                            };
                    spinnerUsers.setAdapter(arrayAdapter);

                    spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            strSelectedUser = strUsers[position];
                            Log.e("selected user",strSelectedUser);
                            getUserId();
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
                Log.e("error: ", String.valueOf(error));
            }
        });
        VolleySingleton.getInstance(HouseActivity.this).addToRequestQueue(stringRequest);
    }

    private void getUserId() {
        ArrayList<UserLangModel> arrayList = new ArrayList<UserLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SIGNUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Display--onResponse:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strUserId = jsonObject1.getString("_id");
                        String strFirstName = jsonObject1.getString("firstName");

                        if(strFirstName.equals(strSelectedUser))
                        {
                            userId=strUserId;
                        }


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

        VolleySingleton.getInstance(HouseActivity.this).addToRequestQueue(stringRequest);


    }


    private void houseApi(String strHouseDeets, String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.HOUSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("House Response ===", "onResponse: " + response);
                Intent i = new Intent(HouseActivity.this, HouseDisplayActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("houseDetails", strHouseDeets);
                map.put("user",userId);

                return map;
            }

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}

