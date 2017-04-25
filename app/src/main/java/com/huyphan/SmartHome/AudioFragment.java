package com.huyphan.SmartHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Huy on Apr 22, 2017.
 */

public class AudioFragment extends Fragment {

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.audio_fragment, container, false);

        Button newPage = (Button)rootView.findViewById(R.id.audio);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AudioActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}

