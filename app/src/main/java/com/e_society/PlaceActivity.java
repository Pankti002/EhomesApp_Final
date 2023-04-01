package com.e_society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class PlaceActivity extends AppCompatActivity {

    EditText edtPlaceDetails, edtRent;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        edtPlaceDetails = findViewById(R.id.edt_placeDetails);
        edtRent=findViewById(R.id.edt_placeRent);
        btnAdd = findViewById(R.id.btn_addPlace);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPlaceDetails = edtPlaceDetails.getText().toString();
                String strRent = edtRent.getText().toString();

                if (strPlaceDetails.length() == 0) {
                    edtPlaceDetails.requestFocus();
                    edtPlaceDetails.setError("FIELD CANNOT BE EMPTY");
                } else if (!strPlaceDetails.matches("[a-zA-Z ]+")) {
                    edtPlaceDetails.requestFocus();
                    edtPlaceDetails.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }else if (strRent.length() == 0) {
                    edtRent.requestFocus();
                    edtRent.setError("FIELD CANNOT BE EMPTY");
                } else if (!strRent.matches("^[0-9]+$")) {
                    edtRent.requestFocus();
                    edtRent.setError("PLEASE ENTER DIGITS ONLY");
                }
                else {
                    placeApi(strPlaceDetails,strRent);
                }
            }
        });
    }

    private void placeApi(String strPlaceDeets,String strRent) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.PLACE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Place Response ===", "onResponse: " + response);
                Intent i = new Intent(PlaceActivity.this, PlaceDisplayActivity.class);
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
                map.put("placeName", strPlaceDeets);
                map.put("rent",strRent);

                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}


