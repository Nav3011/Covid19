package com.example.covid19;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class IndiaFragment extends Fragment {


    public IndiaFragment() {
        // Required empty public constructor
    }
    ArrayList<String> stateCodes = new ArrayList<>();
    List<String> state = new ArrayList<String>();
    ArrayList<String> active = new ArrayList<>();
    ArrayList<String> confirmed = new ArrayList<>();
    ArrayList<String> deaths = new ArrayList<>();
    ArrayList<String> recovered = new ArrayList<>();
    ArrayList<String> deltaC = new ArrayList<>();
    ArrayList<String> deltaD = new ArrayList<>();
    ArrayList<String> deltaR = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_india, container, false);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, state);
        String [] values =
                {"Time at Residence","Under 6 months","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years","Time at Residence","Under 6 months","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years","Time at Residence","Under 6 months","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years"};

        String mUrlString = "https://api.covid19india.org/data.json";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mUrlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("RESPONSE",response);
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
//                            Log.d("STATES",stateCodes.toString());
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
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, state);
        adapter.setDropDownViewResource(R.layout.spinner_line);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

}
