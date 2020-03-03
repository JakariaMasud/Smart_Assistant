package com.example.smartassistant.View;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.Adapter.LocationBasedAdapter;
import com.example.smartassistant.Adapter.OnEventClickListener;
import com.example.smartassistant.Adapter.TimeBasedAdapter;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.LocationBasedEventViewModel;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentLocationBasedBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationBasedFragment extends Fragment {
    LocationBasedAdapter locationBasedAdapter;
    LocationBasedEventViewModel locationBasedEventViewModel;
    RecyclerView.LayoutManager locationLayoutManager;
    SharedPreferences preferences;
    NavController navController;
    List<LocationBasedEvent> locationBasedEventList;
    FragmentLocationBasedBinding locationBasedBinding;


    public LocationBasedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        locationBasedBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_location_based, container, false);
        return locationBasedBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationBasedEventList=new ArrayList<>();
        locationLayoutManager = new LinearLayoutManager(getContext());
        locationBasedBinding.locationListRV.setLayoutManager(locationLayoutManager);
        locationBasedEventViewModel = new ViewModelProvider(this).get(LocationBasedEventViewModel.class);
        locationBasedAdapter = new LocationBasedAdapter(locationBasedEventList);
        locationBasedBinding.locationListRV.setAdapter(locationBasedAdapter);
        locationBasedAdapter.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onEventClick(int position, View v) {
                Toast.makeText(getContext(),"location event clicked",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEventLongClick(int position, View v) {

            }
        });

        locationBasedEventViewModel.getAllEvents().observe(this, new Observer<List<LocationBasedEvent>>() {
            @Override
            public void onChanged(List<LocationBasedEvent> eventList) {
                locationBasedEventList.addAll(eventList);
                if ( locationBasedEventList.size()> 0) {
                    locationBasedBinding.noItemTV.setVisibility(View.GONE);
                    locationBasedAdapter.notifyDataSetChanged();

                } else {
                    locationBasedBinding.noItemTV.setVisibility(View.VISIBLE);
                }



            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int position=item.getGroupId();

        switch(item.getItemId()) {
            case 210:
                Toast.makeText(getContext(),"edit location",Toast.LENGTH_LONG).show();

                return true;
            case 211:
                Toast.makeText(getContext(),"delete location",Toast.LENGTH_LONG).show();

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
