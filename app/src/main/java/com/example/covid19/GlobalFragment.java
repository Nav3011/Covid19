package com.example.covid19;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GlobalFragment extends Fragment {


    public GlobalFragment() {
        // Required empty public constructor
    }
    List<String> code = new ArrayList<String>();
    List<String> total_cases = new ArrayList<String>();
    List<String> total_recovered = new ArrayList<String>();
    List<String> total_deaths = new ArrayList<String>();
    List<String> total_active_cases = new ArrayList<String>();
    List<String> countries_name = new ArrayList<String>();
    TextView total, active, recovered, deaths, countries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_global, container, false);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_default_item, countries_name);


        String mUrlString = "https://api.thevirustracker.com/free-api?global=stats";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mUrlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject data = new JSONObject(response);
                            JSONArray global = data.getJSONArray("results");
                            JSONObject details = new JSONObject();
                            for (int i=1;i<global.length();i++) {
                                details = global.getJSONObject(i);
                                code.add("G");
                                countries_name.add("GLOBAL");
                                total_cases.add(details.getString("total_cases"));
                                total_recovered.add(details.getString("total_recovered"));
                                total_deaths.add(details.getString("total_deaths"));
                                total_active_cases.add(details.getString("total_active_cases"));
                            }
//                            adapter.notifyDataSetChanged();
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


        String mUrlString1 = "https://api.thevirustracker.com/free-api?countryTotals=ALL";
        StringRequest stringRequest1 = new StringRequest(
                Request.Method.GET,
                mUrlString1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject data = new JSONObject(response);
                            JSONArray global = data.getJSONArray("countryitems");
                            Log.d("LIST DETAILS", global.get(0).toString());
                            JSONObject c = new JSONObject(global.get(0).toString());
                            JSONObject details = new JSONObject();
                            for (int i=1;i<c.length();i++) {
                                details = c.getJSONObject(String.valueOf(i));
                                code.add(details.getString("code"));
                                countries_name.add(details.getString("title"));
                                total_cases.add(details.getString("total_cases"));
                                total_recovered.add(details.getString("total_recovered"));
                                total_deaths.add(details.getString("total_deaths"));
                                total_active_cases.add(details.getString("total_active_cases"));
                            }
                            adapter.notifyDataSetChanged();
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest1);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        total = v.findViewById(R.id.total_count);
        active = v.findViewById(R.id.active);
        recovered = v.findViewById(R.id.recover);
        deaths = v.findViewById(R.id.death);
        countries = v.findViewById(R.id.countryName);

        adapter.setDropDownViewResource(R.layout.spinner_item_list_view);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", parent.getItemAtPosition(position).toString());
                countries.setText(countries_name.get(position));
//                update_time.setText("Updated at "+time.get(position).toString());
                total.setText(total_cases.get(position).toString());
                active.setText(total_active_cases.get(position).toString());
//                active_delta.setText("+"+deltaC.get(position).toString());
                recovered.setText(total_recovered.get(position).toString());
//                recover_delta.setText("+"+deltaR.get(position).toString());
                deaths.setText(total_deaths.get(position).toString());
//                death_delta.setText("+"+deltaD.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

}