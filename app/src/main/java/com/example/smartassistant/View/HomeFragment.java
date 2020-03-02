package com.example.smartassistant.View;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.Adapter.LocationBasedAdapter;
import com.example.smartassistant.Adapter.TimeBasedAdapter;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.LocationBasedEventViewModel;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentHomeBinding;

import java.util.List;




public class HomeFragment extends Fragment{
    FragmentHomeBinding homeBinding;
    LocationBasedAdapter locationBasedAdapter;
    TimeBasedAdapter timeBasedAdapter;
    TimeBasedEventViewModel timeBasedEventViewModel;
    LocationBasedEventViewModel locationBasedEventViewModel;
    RecyclerView.LayoutManager timeLayoutManager;
    RecyclerView.LayoutManager locationLayoutManager;
    private static final String TAG = "HomeFragment";
    SharedPreferences preferences;
    NavController navController;




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        preferences=getActivity().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        boolean hasData =preferences.contains("isConfigured");
        Log.e("has data",String.valueOf(hasData));
        if(!hasData){
            navController.navigate(HomeFragmentDirections.actionHomeToConfigurationFragment());

        }
        timeLayoutManager=new LinearLayoutManager(getContext());
        locationLayoutManager =new LinearLayoutManager(getContext());
        homeBinding.timeListRV.setLayoutManager(timeLayoutManager);
        homeBinding.locationListRV.setLayoutManager(locationLayoutManager);
        timeBasedEventViewModel=new ViewModelProvider(this).get(TimeBasedEventViewModel.class);
        locationBasedEventViewModel=new ViewModelProvider(this).get(LocationBasedEventViewModel.class);
        timeBasedEventViewModel.getAllEvents().observe(this, new Observer<List<TimeBasedEvent>>() {
            @Override
            public void onChanged(List<TimeBasedEvent> eventList) {
                Log.e(TAG, eventList.toString());
                timeBasedAdapter=new TimeBasedAdapter(eventList);
                homeBinding.timeListRV.setAdapter(timeBasedAdapter);
                timeBasedAdapter.notifyDataSetChanged();




            }
        });
        locationBasedEventViewModel.getAllEvents().observe(this, new Observer<List<LocationBasedEvent>>() {
            @Override
            public void onChanged(List<LocationBasedEvent> locationBasedEvents) {
                Log.e(TAG, locationBasedEvents.toString());
                locationBasedAdapter=new LocationBasedAdapter(locationBasedEvents);
                homeBinding.locationListRV.setAdapter(locationBasedAdapter);
                locationBasedAdapter.notifyDataSetChanged();
            }
        });





    }


}
