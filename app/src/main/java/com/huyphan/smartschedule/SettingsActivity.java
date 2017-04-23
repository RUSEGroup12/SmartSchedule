package com.huyphan.smartschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
                return true;
            default:
                Toast.makeText(this, "Changes discarded", Toast.LENGTH_SHORT).show();
                startActivity(showMainActivity);
                return super.onOptionsItemSelected(item);
        }

    }
}
