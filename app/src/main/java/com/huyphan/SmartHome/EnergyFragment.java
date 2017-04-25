package com.huyphan.SmartHome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Huy on Apr 22, 2017.
 */

public class EnergyFragment extends Fragment {

    public EnergyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.energy_fragment, container, false);
        return rootView;
    }

}



