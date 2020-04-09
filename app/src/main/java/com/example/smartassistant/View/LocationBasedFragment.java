package com.example.smartassistant.View;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.Adapter.LocationBasedAdapter;
import com.example.smartassistant.Adapter.OnEventClickListener;
import com.example.smartassistant.BroadCastReciver.LocationBasedEventReciever;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.LocationBasedEventViewModel;
import com.example.smartassistant.DI.ViewModelFactory;
import com.example.smartassistant.databinding.FragmentLocationBasedBinding;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationBasedFragment extends Fragment {
    LocationBasedAdapter locationBasedAdapter;
    LocationBasedEventViewModel locationBasedEventViewModel;
    RecyclerView.LayoutManager locationLayoutManager;
    NavController navController;
    List<LocationBasedEvent> locationBasedEventList;
    FragmentLocationBasedBinding locationBasedBinding;

    @Inject
    SharedPreferences preferences;
    @Inject
    GeofencingClient geofencingClient;
    @Inject
    ViewModelFactory viewModelFactory;



    public LocationBasedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
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
        navController = Navigation.findNavController(view);
        locationBasedEventList=new ArrayList<>();
        locationLayoutManager = new LinearLayoutManager(getContext());
        locationBasedBinding.locationListRV.setLayoutManager(locationLayoutManager);
        locationBasedEventViewModel = new ViewModelProvider(this,viewModelFactory).get(LocationBasedEventViewModel.class);
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

        locationBasedEventViewModel.getAllEvents().observe(getViewLifecycleOwner(), new Observer<List<LocationBasedEvent>>() {
            @Override
            public void onChanged(List<LocationBasedEvent> eventList) {
                    locationBasedEventList.clear();
                    for (LocationBasedEvent event:eventList){
                        locationBasedEventList.add(event);
                    }
                    locationBasedAdapter.notifyDataSetChanged();
                if(locationBasedEventList.size()<=0){
                    locationBasedBinding.noItemTV.setVisibility(View.VISIBLE);
                }
                else {
                    locationBasedBinding.noItemTV.setVisibility(View.GONE);
                }



            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int position=item.getGroupId();
        if(getUserVisibleHint()){
            LocationBasedEvent event=  locationBasedEventList.get(position);
            switch(item.getItemId()) {
                case 210:
                    ViewAllFragmentDirections.ActionViewAllFragmentToEditLocationEventFragment action=ViewAllFragmentDirections.actionViewAllFragmentToEditLocationEventFragment(event.getId());
                    navController.navigate(action);
                    return true;
                case 211:
                    deleteEvent(event);

                    return true;

                default:
                    return true;
            }
        }
        else{
            return false;
        }


    }

    private void deleteEvent(final LocationBasedEvent event) {

                 //delete from geo fence
      geofencingClient.removeGeofences(getGeofencePendingIntent(event.getId()))
              .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
              //delete from database
              locationBasedEventViewModel.deleteById(event.getId());
              Toast.makeText(getContext(), "successfully deleted", Toast.LENGTH_SHORT).show();


          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {

          }
      });


    }
    private PendingIntent getGeofencePendingIntent(String id) {
        Intent intent = new Intent(getContext(), LocationBasedEventReciever.class);
        intent.putExtra("locationEventId",id);
        PendingIntent geofencePendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }
}
