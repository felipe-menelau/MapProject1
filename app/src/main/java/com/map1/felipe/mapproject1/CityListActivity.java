package com.map1.felipe.mapproject1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.*;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class CityListActivity extends Activity {

    private JSONObject weather;
    private ListView mainList;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        Intent intent = getIntent();
        MarkerOptions marker = intent.getParcelableExtra("marker");
        LatLng cord = marker.getPosition();
        final String link = "http://api.openweathermap.org/data/2.5/find?lat=" + cord.latitude + "&lon=" + cord.longitude + "&cnt=10&appid=03aa41b45c8924319339222bf28ad662";
        final boolean[] done = {false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weather = new JSONObject(readUrl(link));
                    done[0] = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        mainList = (ListView) findViewById(R.id.list);
        while(!done[0]){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> cidades = new ArrayList<String>();
        try {
            for (int i = 0; i < weather.getJSONArray("list").length(); i++){
                cidades.add(weather.getJSONArray("list").getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, cidades);
        mainList.setAdapter(listAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityListActivity.this, CityDetails.class);
                intent.putExtra("json", weather.toString());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
