package com.example.covid19;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class IndiaFragment extends Fragment {

    List<String> stateCodes = new ArrayList<String>();
    List<String> state = new ArrayList<String>();
    List<String> active = new ArrayList<String>();
    List<String> confirmed = new ArrayList<String>();
    List<String> deaths = new ArrayList<String>();
    List<String> recovered = new ArrayList<String>();
    List<String> deltaC = new ArrayList<String>();
    List<String> deltaD = new ArrayList<String>();
    List<String> deltaR = new ArrayList<String>();
    List<String> time = new ArrayList<String>();

    public IndiaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_india, container, false);


//        final TextView stats = (TextView) v.findViewById(R.id.stats);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_default_item, state);

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
        final TextView stateName, update_time, total_count, active_count, active_delta, recover_count, recover_delta, death_count, death_delta;
        stateName = (TextView) v.findViewById(R.id.stateName);
        update_time = (TextView) v.findViewById(R.id.update_time);
        total_count = (TextView) v.findViewById(R.id.total_count);
        active_count = (TextView) v.findViewById(R.id.active_count);
        active_delta = (TextView) v.findViewById(R.id.active_delta);
        recover_count = (TextView) v.findViewById(R.id.recover_count);
        recover_delta = (TextView) v.findViewById(R.id.recover_delta);
        death_count = (TextView) v.findViewById(R.id.death_count);
        death_delta = (TextView) v.findViewById(R.id.death_delta);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_default_item, state);
        adapter.setDropDownViewResource(R.layout.spinner_item_list_view);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", parent.getItemAtPosition(position).toString());
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG);
//                stats.setText("Active : "+active.get(position));
                stateName.setText(state.get(position));
                update_time.setText("Updated at "+time.get(position).toString());
                total_count.setText(confirmed.get(position).toString());
                active_count.setText(active.get(position).toString());
                active_delta.setText("+"+deltaC.get(position).toString());
                recover_count.setText(recovered.get(position).toString());
                recover_delta.setText("+"+deltaR.get(position).toString());
                death_count.setText(deaths.get(position).toString());
                death_delta.setText("+"+deltaD.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

}
