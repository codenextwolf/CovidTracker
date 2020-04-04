package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class SavedCountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_countries);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String savedCountries = sharedPreferences.getString("saved_countries", "us");
        String[] savedCountriesArray = savedCountries.split(",");

        ListView savedCountriesListView = findViewById(R.id.saved_countries_list_view);
        SavedCountriesAdapter savedCountriesAdapter = new SavedCountriesAdapter(this, savedCountriesArray);
        savedCountriesListView.setAdapter(savedCountriesAdapter);
    }
}
