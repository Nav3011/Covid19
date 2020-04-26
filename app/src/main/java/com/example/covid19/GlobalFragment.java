package com.example.covid19;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalFragment extends Fragment {


    public GlobalFragment() {
        // Required empty public constructor
    }
    String active_count, total_count, deaths_count, recovered_count, countries_count;
    TextView total, active, recovered, deaths, countries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_global, container, false);
        String mUrlString = "https://api.thevirustracker.com/free-api?global=stats";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mUrlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("RESPONSE",response);
                        try {
                            JSONObject data = new JSONObject(response);
                            JSONArray global = data.getJSONArray("results");
                            JSONObject stats = global.getJSONObject(0);
                            active_count = stats.getString("total_active_cases");
                            total_count = stats.getString("total_cases");
                            deaths_count = stats.getString("total_deaths");
                            recovered_count = stats.getString("total_recovered");
                            countries_count = stats.getString("total_affected_countries");
                            Log.d("ACTIVE", active_count);
                        }

                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        total = v.findViewById(R.id.total_count);
        active = v.findViewById(R.id.active);
        recovered = v.findViewById(R.id.recover);
        deaths = v.findViewById(R.id.death);
        countries = v.findViewById(R.id.countries);

        total.setText(total_count);
        active.setText(active_count);
        recovered.setText(recovered_count);
        deaths.setText(deaths_count);
        countries.setText(countries_count);
        return v;
    }

}