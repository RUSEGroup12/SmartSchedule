package com.huyphan.SmartHome;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class SettingsActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "MySettings";
    public String activeField = "home";
    public String homeLocation = ""; public String homeLat = ""; public String homeLong = "";
    public String workLocation = ""; public String workLat = ""; public String workLong = "";

    TextView workStartTimeTextView;
    TextView timeToGetReadyTextView;
    TextView homeLocationTextView;
    TextView workLocationTextView;

    int timeToGetReadyInMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        workStartTimeTextView = (TextView) findViewById(R.id.work_start_time_text_view);
        timeToGetReadyTextView = (TextView) findViewById(R.id.time_to_get_ready_text_view);
        homeLocationTextView = (TextView) findViewById(R.id.home_location);
        workLocationTextView = (TextView) findViewById(R.id.work_location);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        workStartTimeTextView.setText(settings.getString("workStartTime", "00:00"));
        timeToGetReadyTextView.setText(settings.getString("timeToGetReady", "0"));

        timeToGetReadyInMinutes = Integer.parseInt(settings.getString("timeToGetReady", "0"));

        homeLocation = settings.getString("homeLocation", "To be added from Google Maps");
        homeLocationTextView.setText(homeLocation);

        workLocation = settings.getString("workLocation", "To be added from Google Maps");
        workLocationTextView.setText(workLocation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent showMainActivity = new Intent(this, MainActivity.class);
        startActivity(showMainActivity);
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void selectTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new
                TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        workStartTimeTextView.setText(hourOfDay + ":" + prettyMinute(minute));
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("workStartTime", workStartTimeTextView.getText().toString());
                        editor.commit();
                    }
                }, 6, 0, false);
        timePickerDialog.show();
    }

    public void increaseTime(View view){
        timeToGetReadyInMinutes += 5;
        timeToGetReadyTextView.setText(String.valueOf(timeToGetReadyInMinutes));

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("timeToGetReady", timeToGetReadyTextView.getText().toString());
        editor.commit();

    }
    public void decreaseTime(View view){
        if (timeToGetReadyInMinutes>0){
            timeToGetReadyInMinutes -= 5;
        }
        timeToGetReadyTextView.setText(String.valueOf(timeToGetReadyInMinutes));

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("timeToGetReady", timeToGetReadyTextView.getText().toString());
        editor.commit();
    }


    public String prettyMinute(int minute){
        if (minute==0){
            return "00";
        }
        return String.valueOf(minute);
    }

    //SEARCH PLACES

    public void findHomeLocation(View view){
        activeField = "home";
        findPlace(view);
    }

    public void findWorkLocation(View view){
        activeField = "work";
        findPlace(view);
    }

    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Intent intent = new Intent(this, GoogleMapActivity.class);
                intent.putExtra("latitude",place.getLatLng().latitude);
                intent.putExtra("longitute",place.getLatLng().longitude);
                intent.putExtra("name",place.getName());
                intent.putExtra("address",place.getAddress());
                startActivity(intent);

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                if (activeField == "home") {

                    String textToDisplay = place.getName() + ", " + place.getAddress() + "\n" +
                            "Latitude: " + place.getLatLng().latitude + ", Longtitude: " + place.getLatLng().longitude;

                    homeLocationTextView.setText(textToDisplay);

                    homeLocation = textToDisplay;
                    homeLat = String.valueOf(place.getLatLng().latitude);
                    homeLong = String.valueOf(place.getLatLng().longitude);
                    editor.putString("homeLocation", homeLocation);
                    editor.putString("homeLat", homeLat);
                    editor.putString("homeLong", homeLong);
                    editor.commit();

                } else {

                    String textToDisplay = place.getName() + ", " + place.getAddress() + "\n" +
                            "Latitude: " + place.getLatLng().latitude + ", Longtitude: " + place.getLatLng().longitude;

                    workLocationTextView.setText(textToDisplay);
                    workLocation = textToDisplay;
                    workLat = String.valueOf(place.getLatLng().latitude);
                    workLong = String.valueOf(place.getLatLng().longitude);
                    editor.putString("workLocation", workLocation);
                    editor.putString("workLat", workLat);
                    editor.putString("workLong", workLong);
                    editor.commit();
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}


