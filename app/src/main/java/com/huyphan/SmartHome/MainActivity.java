package com.huyphan.SmartHome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MySettings";
    String homeLat; String homeLong;
    String workLat; String workLong;
    int eta;

    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mFragmentAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        homeLat = settings.getString("homeLat", ""); homeLong = settings.getString("homeLong", "");
        workLat = settings.getString("workLat", ""); workLong = settings.getString("workLong", "");

        Log.i("INFO", "Task has been executed");
        Log.i("HomeLat", homeLat); Log.i("HomeLong", homeLong);
        Log.i("WorkLat", workLat); Log.i("WorkLong", workLong);

        DownloadTask task = new DownloadTask();

        String URL_REQUEST = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + homeLat + "," + homeLong +
                "&destination=" + workLat + "," + workLong +
                "&key=AIzaSyCpl9VDsbJ_-tmb2askZQ-nABj_pj6PVh4";

        task.execute(URL_REQUEST);
        Log.i("URL", URL_REQUEST);
        Log.i("INFO", "Task has been executed");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent showInfo = new Intent(this, AboutActivity.class);
                startActivity(showInfo);
                return true;

            case R.id.action_settings:
                Intent showSettings = new Intent(this, SettingsActivity.class);
                startActivity(showSettings);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("Google Map: ", result);

            int etaResult = jsonToMinutes(result);
            Log.i("ETA", "" + eta);

            eta = etaResult;

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("eta", etaResult);
            editor.commit();
        }
    }


    public static int jsonToMinutes(String input){


        int duration = 0 ;

        try{

            JSONObject googleJSON  = new JSONObject(input); // json
            duration = googleJSON.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getInt("value");
            duration /= 60;
            return duration;

        }catch(JSONException pe){

            System.out.println("position: " + pe.getMessage());
            System.out.println(pe);
        }

        return 0; // return 0 if try statement fails

    }

}

