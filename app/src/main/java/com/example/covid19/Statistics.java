package com.example.covid19;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class Statistics extends AppCompatActivity {

    private String mUrlString = "https://api.covid19india.org/data.json";
    private Context mContext;
    public Statistics(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generate_stats();
    }

    public void generate_stats() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mUrlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("RESPONSE",response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR",error.toString());
                    }
                }
        );
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
}
