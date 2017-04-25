package com.huyphan.SmartHome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EnergyActivity extends AppCompatActivity {

    public static final String apiurl = "http://9aa4017f.ngrok.io/api/temp/preferred";

    SharedPreferences settings;
    TextView prefTemp;
    TextView currentTemp;
    TextView t2tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        //getviews
        prefTemp = (TextView) findViewById(R.id.preferred_temp);
        currentTemp = (TextView) findViewById(R.id.current_temp);
        t2tView = (TextView) findViewById(R.id.time2temp);

        //settings
        settings = this.getSharedPreferences("comrusegroup12.github.se", Context.MODE_PRIVATE);

        //get from settings cache and update the views
        prefTemp.setText(String.valueOf(settings.getInt("prefTemp", 72)));
        currentTemp.setText(String.valueOf(settings.getInt("currentTemp", 72)));
        t2tView.setText(settings.getString("t22", "0.0"));


    }

    public void dec(View view) {
        //decrement the temp in data
        int x = settings.getInt("prefTemp", 73);
        x--;
        settings.edit().putInt("prefTemp", x).commit();
        //update the views
        prefTemp = (TextView) findViewById(R.id.preferred_temp);
        prefTemp.setText(String.valueOf(x));

    }

    public void inc(View view) {
        //increment the temp in data
        int x = settings.getInt("prefTemp", 73);
        x++;
        settings.edit().putInt("prefTemp", x).commit();
        //update the view with the new temp
        prefTemp = (TextView) findViewById(R.id.preferred_temp);
        prefTemp.setText(String.valueOf(x));
    }

    public void setFunc(View view) {
        //calculates the temperature
        int cTemp = settings.getInt("currentTemp", 73);
        int pTemp = settings.getInt("prefTemp", 73);
        String t2t = String.valueOf(t2t(cTemp, pTemp, 0.3, 7));
        if(!(t2t.equals("0.0"))) {
            t2t = t2t.substring(0, 4);
        }
        //update cache
        settings.edit().putString("t2t", t2t).commit();

        //update the t2t view
        t2tView = (TextView) findViewById(R.id.time2temp);
        t2tView.setText(t2t);

        //network
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("======================", response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.d("======================", error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("preferred", String.valueOf(settings.getInt("prefTemp", 73))); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public double t2t(int current, int preferred, double k, int helpConst){
        if(preferred>current) {
            double x = Math.exp((double) current / (double) preferred);
            double t = -(helpConst / (x * k)) * Math.log((0.01 * preferred) / (preferred - current));
            return t;
        }
        else {
            int y = current - preferred;
            preferred = current +y;
            double x = Math.exp((double) current / (double) preferred);
            double t = -(helpConst / (x * k)) * Math.log((0.01 * preferred) / (preferred - current));
            return t;
        }
    }

    public void homeSetting(View view){
        //update the temp in data
        int x = 76;
        settings.edit().putInt("prefTemp", x).commit();
        //update the views
        prefTemp = (TextView) findViewById(R.id.preferred_temp);
        prefTemp.setText(String.valueOf(x));
        setFunc(view);
        return;
    }

    public void awaySetting(View view){
        int prefT = 68;
        settings.edit().putInt("prefTemp", prefT).commit();
        //update the views
        prefTemp = (TextView) findViewById(R.id.preferred_temp);
        prefTemp.setText(String.valueOf(prefT));
        setFunc(view);
        return;
    }


}
