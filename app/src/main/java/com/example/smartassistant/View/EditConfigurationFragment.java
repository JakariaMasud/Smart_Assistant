package com.example.smartassistant.View;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentEditConfigurationBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditConfigurationFragment extends Fragment {
    FragmentEditConfigurationBinding editConfigurationBinding;
    List<Integer> idList;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    NavController navController;
    int selectedSim=111;
    String msgText,notificationTitle,notificationDescription;


    public EditConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editConfigurationBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_configuration, container, false);
        return editConfigurationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idList=new ArrayList<>();
        navController= Navigation.findNavController(view);
        preferences=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        sfEditor=preferences.edit();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            Log.e("sim","permission available");

            SubscriptionManager subscriptionManager = SubscriptionManager.from(getContext());
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            int totalSim=subscriptionInfoList.size();
            for(int i=0;i<totalSim;i++){
                Chip chip=new Chip(getContext());
                int id=View.generateViewId();
                chip.setId(id);
                idList.add(id);
                ChipDrawable drawable= ChipDrawable.createFromAttributes(getContext(),null,0,R.style.CustomChipChoice);
                chip.setChipDrawable(drawable);
                chip.setText(subscriptionInfoList.get(i).getCarrierName());
                editConfigurationBinding.editSlectionSimChipGroup.addView(chip);
            }
            msgText=preferences.getString("msgText",null);
            notificationTitle=preferences.getString("notificationTitle",null);
            notificationDescription=preferences.getString("notificationDescription",null);
            selectedSim=preferences.getInt("selectedSim",111);
            editConfigurationBinding.editMessageET.setText(msgText);
            editConfigurationBinding.editNotificationTitleET.setText(notificationTitle);
            editConfigurationBinding.editNotificationTitleET.setText(notificationDescription);
            if(selectedSim==idList.get(0)){
                editConfigurationBinding.editSlectionSimChipGroup.check(selectedSim);
            }
            else if(selectedSim==idList.get(1)){
                editConfigurationBinding.editSlectionSimChipGroup.check(selectedSim);
            }






        }
        editConfigurationBinding.editSlectionSimChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if(checkedId==idList.get(0)){
                    selectedSim=idList.get(0);
                }
                else if(checkedId==idList.get(1)){
                    selectedSim=idList.get(1);
                }

            }
        });

    }
}
