package com.e_society.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.e_society.R;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.model.PlaceLangModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class PlaceUpdateActivity extends AppCompatActivity {

    EditText edtPlaceDetails, edtRent;
    Button btnAddPlace,btnDeletePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Intent i = getIntent();

        edtPlaceDetails = findViewById(R.id.edt_placeDetails);
        edtRent=findViewById(R.id.edt_placeRent);
        btnAddPlace = findViewById(R.id.btn_addPlace);
        btnDeletePlace = findViewById(R.id.btn_delete_place);


        String placeId = i.getStringExtra("PLACE_ID");
        String strPlaceDeets = i.getStringExtra("PLACE_NAME");
        String strRent=i.getStringExtra("RENT");

        PlaceLangModel placeLangModel = new PlaceLangModel();
        edtPlaceDetails.setText(strPlaceDeets);
        edtRent.setText(strRent);

        //normal code
        btnAddPlace.setText("Update Place");
        btnDeletePlace.setVisibility(View.VISIBLE);
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPlaceDetails = edtPlaceDetails.getText().toString();
                String strRent=edtRent.getText().toString();
                if (strPlaceDetails.length() == 0) {
                    edtPlaceDetails.requestFocus();
                    edtPlaceDetails.setError("FIELD CANNOT BE EMPTY");
                } else if (!strPlaceDetails.matches("[a-zA-Z ]+")) {
                    edtPlaceDetails.requestFocus();
                    edtPlaceDetails.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                } else if (strRent.length() == 0) {
                    edtRent.requestFocus();
                    edtRent.setError("FIELD CANNOT BE EMPTY");
                } else if (!strRent.matches("^[0-9]+$")) {
                    edtRent.requestFocus();
                    edtRent.setError("PLEASE ENTER DIGITS ONLY");
                }
                else {
                    Toast.makeText(PlaceUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();

                    apiCall(placeId, strPlaceDetails,strRent);
                }

            }
        });
        btnDeletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAPI(placeId);
            }
        });

    }



    private void deleteAPI(String id) {

        Log.e("TAG****", "deleteAPI Update " + id);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Utils.PLACE_URL + "/" + id, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(PlaceUpdateActivity.this, PlaceDisplayActivity.class);
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
                hashMap.put("placeId", id);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(PlaceUpdateActivity.this).addToRequestQueue(stringRequest);


    }

    private void apiCall(String id, String strPlaceDeets,String strRent) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Utils.PLACE_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(PlaceUpdateActivity.this, PlaceDisplayActivity.class);
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
                hashMap.put("placeId", id);
                hashMap.put("placeName", strPlaceDeets);
                hashMap.put("rent", strRent);


                return hashMap;
            }
        };
        VolleySingleton.getInstance(PlaceUpdateActivity.this).addToRequestQueue(stringRequest);

    }
}