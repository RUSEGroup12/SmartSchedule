package com.huyphan.smartschedule;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "MySettings";

    TextView workStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        workStartTime = (TextView) findViewById(R.id.work_start_time_text_view);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String time = settings.getString("workStartTime", "00:00");
        workStartTime.setText(time);
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
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
                startActivity(showMainActivity);

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("workStartTime", workStartTime.getText().toString());
                // Commit the edits!
                editor.commit();

                return true;
            default:
                Toast.makeText(this, "Changes discarded", Toast.LENGTH_SHORT).show();
                startActivity(showMainActivity);
                return super.onOptionsItemSelected(item);
        }

    }


    public void selectTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new
                TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        workStartTime.setText(hourOfDay + ":" + prettyMinute(minute));
                    }
                }, 6, 0, false);
        timePickerDialog.show();
    }

    public String prettyMinute(int minute){
        if (minute==0){
            return "00";
        }
        return String.valueOf(minute);
    }

}


