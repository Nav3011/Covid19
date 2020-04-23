package com.example.covid19;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    private String mUrlString = "https://api.covid19india.org/data.json";
    private Context mContext;
    public ArrayList<String> stateCodes = new ArrayList<>();
    public ArrayList<String> state = new ArrayList<>();
    ArrayList<String> active = new ArrayList<>();
    ArrayList<String> confirmed = new ArrayList<>();
    ArrayList<String> deaths = new ArrayList<>();
    ArrayList<String> recovered = new ArrayList<>();
    ArrayList<String> deltaC = new ArrayList<>();
    ArrayList<String> deltaD = new ArrayList<>();
    ArrayList<String> deltaR = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();

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
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                }
        );
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void parseResponse(String response) {

        try {
            JSONObject data = new JSONObject(response);
            JSONArray statewise = data.getJSONArray("statewise");
            for (int i=0; i< statewise.length(); i++) {
                JSONObject stateDetails = statewise.getJSONObject(i);
                active.add(stateDetails.getString("active"));
                confirmed.add(stateDetails.getString("confirmed"));
                deaths.add(stateDetails.getString("deaths"));
                deltaC.add(stateDetails.getString("deltaconfirmed"));
                deltaD.add(stateDetails.getString("deltadeaths"));
                deltaR.add(stateDetails.getString("deltarecovered"));
                time.add(stateDetails.getString("lastupdatedtime"));
                recovered.add(stateDetails.getString("recovered"));
                state.add(stateDetails.getString("state"));
                stateCodes.add(stateDetails.getString("statecode"));
            }
//            Log.d("STATES from STATISTICS",state.toString());
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
