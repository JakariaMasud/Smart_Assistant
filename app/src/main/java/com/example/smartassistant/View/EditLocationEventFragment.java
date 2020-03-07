package com.example.smartassistant.View;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.BroadCastReciver.LocationBasedEventReciever;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.LocationBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentEditLocationEventBinding;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditLocationEventFragment extends Fragment {
    FragmentEditLocationEventBinding editLocationEventBinding;
    String eventId;
    LocationBasedEventViewModel locationBasedEventViewModel;
    String title;
    float radiusInMeter;
    double latitude;
    double longitude;
    String address;
    GeofencingClient geofencingClient;
    NavController navController;
    int LOCATION_REQUEST_CODE =123;
    Intent locationPickerIntent;
    private PendingIntent geofencePendingIntent;


    public EditLocationEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editLocationEventBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_location_event, container, false);
        return editLocationEventBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationBasedEventViewModel=new ViewModelProvider(this).get(LocationBasedEventViewModel.class);
        geofencingClient = LocationServices.getGeofencingClient(getContext());
        navController = Navigation.findNavController(view);
        if(getArguments()!=null){
            eventId= EditLocationEventFragmentArgs.fromBundle(getArguments()).getEventId();

            settingUpUi();


        }
    }

    private void settingUpUi() {

        LocationBasedEvent event=locationBasedEventViewModel.getById(eventId);
        title=event.getTitle();
        address=event.getAddress();
        radiusInMeter=event.getRadiusInMeter();
        editLocationEventBinding.editTitleET.setText(title);
        editLocationEventBinding.editSelectedLocationTV.setText(address);
        editLocationEventBinding.editAreaET.setText(String.valueOf(radiusInMeter));
        settingUpListener();
        

    }

    private void settingUpListener() {
        editLocationEventBinding.editLocationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPickerIntent = new LocationPickerActivity.Builder()
                        .withLocation(23.810331, 90.412521)
                        .withGeolocApiKey("AIzaSyAvHCTyXb0ef26I2i5SPwrygEnblHOweBs")
                        .withSearchZone("bn")
                        .shouldReturnOkOnBackPressed()
                        .withMapStyle(R.raw.my_style)
                        .withGooglePlacesEnabled()
                        .withGoogleTimeZoneEnabled()
                        .withUnnamedRoadHidden()
                        .build(getContext());

                startActivityForResult(locationPickerIntent, LOCATION_REQUEST_CODE);
            }
        });
        editLocationEventBinding.editAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLocationEventBinding.editLocationProgress.setVisibility(View.VISIBLE);
                checkingUserInput();

            }
        });
    }

    private void checkingUserInput() {
        Log.e("check","checking user input");
        String radius=editLocationEventBinding.editAreaET.getText().toString();
        title=editLocationEventBinding.editTitleET.getText().toString();
        if(TextUtils.isEmpty(title)){
            editLocationEventBinding.editTitleET.setError("title field can not bee empty");
            editLocationEventBinding.editLocationProgress.setVisibility(View.GONE);
            return;
        }
        else {
            if(TextUtils.isEmpty(radius)){
                editLocationEventBinding.editAreaET.setError("area field  can not be empty ");
                editLocationEventBinding.editLocationProgress.setVisibility(View.GONE);
                return;
            }
            else {
                if (TextUtils.isEmpty(address)) {
                    Snackbar.make(editLocationEventBinding.rootEditLocation, "must select a location", Snackbar.LENGTH_LONG).show();
                    editLocationEventBinding.editLocationProgress.setVisibility(View.GONE);
                    return;
                } else {
                    Snackbar.make(editLocationEventBinding.rootEditLocation, "checking successful", Snackbar.LENGTH_LONG).show();
                    radiusInMeter = Float.parseFloat(radius.trim());
                    addGeofence();
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==LOCATION_REQUEST_CODE && data!=null){
            latitude = data.getDoubleExtra(LATITUDE, 0.0);
            longitude  = data.getDoubleExtra(LONGITUDE, 0.0);
            address = data.getStringExtra(LOCATION_ADDRESS);
            editLocationEventBinding.editSelectedLocationTV.setText(address);


        }


    }
    private void addGeofence() {
        final LocationBasedEvent event=new LocationBasedEvent(eventId,title,radiusInMeter,latitude,longitude,address);
        Geofence geofence = new Geofence.Builder()
                .setRequestId(eventId) // Geofence ID
                .setCircularRegion( latitude, longitude, radiusInMeter) // defining fence region
                .setExpirationDuration( Geofence.NEVER_EXPIRE ) // expiring date
                // Transition types that it should look for
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();

        GeofencingRequest request = new GeofencingRequest.Builder()
                // Notification to trigger when the Geofence is created
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence ) // add a Geofence
                .build();
        PendingIntent geofencePendingIntent=getGeofencePendingIntent(eventId);
        geofencingClient.addGeofences(request,geofencePendingIntent).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                locationBasedEventViewModel.insert(event);
                Toast.makeText(getContext(),"successfully  updated the geo fences",Toast.LENGTH_LONG).show();
                editLocationEventBinding.editLocationProgress.setVisibility(View.GONE);
                navController.navigate(EditLocationEventFragmentDirections.actionEditLocationEventFragmentToHome());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("exception",e.getMessage());
                Log.e("exception",e.toString());

            }
        });

    }
    private PendingIntent getGeofencePendingIntent(String id) {
        Intent intent = new Intent(getContext(), LocationBasedEventReciever.class);
        intent.putExtra("locationEventId",id);
        geofencePendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }
}
