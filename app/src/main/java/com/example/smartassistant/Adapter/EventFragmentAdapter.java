package com.example.smartassistant.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.smartassistant.View.LocationBasedFragment;
import com.example.smartassistant.View.TimeBasedFragment;

public class EventFragmentAdapter extends FragmentPagerAdapter {
    public EventFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TimeBasedFragment();

            case 1:
                return new LocationBasedFragment();

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Time Based Event";
            case 1:
                return "Location Based Event";
            default:
                return null;
        }
    }
}
