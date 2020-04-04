package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class SavedCountriesAdapter extends ArrayAdapter<String> {

    public SavedCountriesAdapter(Context context, String[] savedCountriesArray) {
        super(context, 0, savedCountriesArray);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.saved_countries_list_item, parent, false);
        final String currentCountryName = getItem(position);
        TextView savedCountryNameTextView = convertView.findViewById(R.id.saved_country_name);
        savedCountryNameTextView.setText(currentCountryName);
        final TextView savedCountryConfirmedTextView = convertView.findViewById(R.id.saved_country_confirmed);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://api.covid19api.com/summary";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            JSONArray countriesArray = responseObject.getJSONArray("Countries");
                            for(int i = 0; i < countriesArray.length(); i++) {
                                JSONObject currentCountry = countriesArray.getJSONObject(i);
                                String currentCountryString = currentCountry.getString("Country").toLowerCase();


                                //check if the current country in the loop is the country being searched
                                if(currentCountryString.equals(currentCountryName)) {
                                    savedCountryConfirmedTextView.setText(currentCountry.getString("TotalConfirmed"));
                                }
                            }
                        } catch (Exception e) {
                            savedCountryConfirmedTextView.setText("Country not found");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                savedCountryConfirmedTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        return convertView;
    }

}

