package com.map1.felipe.mapproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class CityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        int i = intent.getIntExtra("position", 0);
        JSONObject json1 = null;
        String cityname = null, maxtemp = null, mintemp = null, description = null;
        try {
            json1 = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cityname = json1.getJSONArray("list").getJSONObject(i).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            maxtemp = json1.getJSONArray("list").getJSONObject(i).getJSONObject("main").getString("temp_max");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mintemp = json1.getJSONArray("list").getJSONObject(i).getJSONObject("main").getString("temp_min");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            description = json1.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText(String.format("City name:  %s\nMinimum Temperature: %s\nMaximum Temperature: %s\nDescription: %s",
                cityname, mintemp, maxtemp, description));

    }

}
