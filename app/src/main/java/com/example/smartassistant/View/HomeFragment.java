package com.example.smartassistant.View;


import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBinding.silentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionNotifications = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission. ACCESS_NOTIFICATION_POLICY);
                boolean isGiven=permissionNotifications== PackageManager.PERMISSION_GRANTED;
                if(!isGiven) {
                    NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                }
                else {
                    AudioManager audiomanage = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(getContext(), "phone has been silenced", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
