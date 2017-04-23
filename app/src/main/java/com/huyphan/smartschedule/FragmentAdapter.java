package com.huyphan.smartschedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Huy on Apr 22, 2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ScheduleFragment();
            case 1:
                return new EnergyFragment();
            case 2:
                return new AudioFragment();
        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Schedule";
            case 1:
                return "Energy";
            case 2:
                return "Audio";
        }
        return null;
    }


    @Override
    public int getCount() { return 3; }
}

