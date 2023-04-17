package com.e_society.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.e_society.display.PlaceDisplayActivity;
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
    TextView tvUser;

    String user;
    Spinner spinnerUsers;

//    @Override
//    public void onBackPressed() {
//        Intent intent =new Intent(HouseUpdateActivity.this, PlaceDisplayActivity.class);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        Intent i = getIntent();

        Log.e(String.valueOf(i),"");

        edtDeets = findViewById(R.id.edt_House_Deets);
        btnAddHouse = findViewById(R.id.btn_addHouse);
        btnDelete = findViewById(R.id.btn_delete_House);
        spinnerUsers=findViewById(R.id.spinner_users);
        spinnerUsers.setVisibility(View.GONE);

        tvUser=findViewById(R.id.tvUser);

        String strHouseDeets = i.getStringExtra("HOUSE_DEETS");
        String HouseId = i.getStringExtra("HOUSE_ID");
        user=i.getStringExtra("USER");
        getUserId();

        HouseLangModel houseLangModel = new HouseLangModel();
        edtDeets.setText(strHouseDeets);


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
                else if(!strHouseDeets.matches("[a-zA-Z0-9\\s,.'-]+"))
                {
                    edtDeets.requestFocus();
                    edtDeets.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else {
                    Toast.makeText(HouseUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    Log.e(strHouseDeets,"");
                    apiCall(HouseId, strHouseDeets);
                }

            }
        });

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

                        if(user.equals(strUserId))
                        {
                            tvUser.setText(strFirstName);
                            tvUser.setVisibility(View.VISIBLE);

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
                hashMap.put("user",user);

                return hashMap;

            }
        };
        VolleySingleton.getInstance(HouseUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}