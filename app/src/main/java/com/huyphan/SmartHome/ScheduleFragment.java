package com.huyphan.SmartHome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Huy on Apr 22, 2017.
 */

public class ScheduleFragment extends Fragment {


    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);

        Log.i("Fragment View", "Fragment is Created");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment View", "Fragment is RESUMED");
    }
}



