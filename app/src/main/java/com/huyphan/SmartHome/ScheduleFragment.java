package com.huyphan.SmartHome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Huy on Apr 22, 2017.
 */

public class ScheduleFragment extends Fragment {

    int eta;
    int timeToGetReady;
    String workStartTime;
    String homeLocation; String workLocation;
    TextView homeLocationTextView;
    TextView workLocationTextView;
    TextView leaveTextView;
    TextView workStartTextView;
    TextView readyTextView;
    TextView durationTextView;


    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);

        leaveTextView = (TextView) rootView.findViewById(R.id.leave_text_view);
        workStartTextView = (TextView) rootView.findViewById(R.id.work_start_text_view);
        readyTextView = (TextView) rootView.findViewById(R.id.ready_text_view);
        durationTextView = (TextView) rootView.findViewById(R.id.duration_text_view);
        homeLocationTextView = (TextView) rootView.findViewById(R.id.home_location);
        workLocationTextView = (TextView) rootView.findViewById(R.id.work_location);


        SharedPreferences settings = this.getActivity().getSharedPreferences("MySettings", 0);

        workStartTime = settings.getString("workStartTime", "00:00");
        workStartTextView.setText("Your work starts at " + workStartTime);

        timeToGetReady = Integer.valueOf(settings.getString("timeToGetReady", "0"));
        readyTextView.setText("You need " + timeToGetReady + " minutes to get ready");

        eta = settings.getInt("eta", 0);
        durationTextView.setText("It takes you " + eta + " minutes to get to work");


        timeToGetReady = Integer.valueOf(settings.getString("timeToGetReady", "0"));
        workStartTime = settings.getString("workStartTime", "00:00");


        homeLocation = settings.getString("homeLocation", "To be added from Google Maps");
        homeLocationTextView.setText(homeLocation);
        workLocation = settings.getString("workLocation", "To be added from Google Maps");
        workLocationTextView.setText(workLocation);

        leaveTextView.setText("You should leave at " + timeDifference(workStartTime, timeToGetReady, eta));



        return rootView;
    }

    public String timeDifference(String workStart, int ready, int eta) {

        String[] time = workStart.split ( ":" );
        int hour = Integer.parseInt ( time[0].trim() );
        int min = Integer.parseInt ( time[1].trim() );

        int product = hour*60 + min;
        int result = product - ready - eta;
        int resultHour = result/60;
        int resultMinute = result - resultHour*60;

        return resultHour + ":" + prettyMinute(resultMinute);
    }

    public String prettyMinute(int minute){
        if (minute==0){
            return "00";
        }
        if (minute < 10){
            return "0" + minute;
        }
        return String.valueOf(minute);
    }

}



