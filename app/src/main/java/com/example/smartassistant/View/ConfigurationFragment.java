package com.example.smartassistant.View;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentConfigurationBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {
    FragmentConfigurationBinding configurationBinding;
    List<Integer>idList;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    NavController navController;
    int selectedSim=111;
    String msgText,notificationTitle,notificationDescription;


    public ConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        configurationBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_configuration, container, false);
        return configurationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idList=new ArrayList<>();
        navController= Navigation.findNavController(view);
        preferences=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        sfEditor=preferences.edit();
        boolean hasData =preferences.contains("isConfigured");
        if(!hasData){
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Configuration Needed")
                    .setMessage("you have to set the configuration to use the App")
                    .setPositiveButton("Ok", null)
                    .show();
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            Log.e("sim","permission available");

            SubscriptionManager subscriptionManager = SubscriptionManager.from(getContext());
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            int totalSim=subscriptionInfoList.size();
            Log.e("all sim",subscriptionInfoList.toString());
            Log.e("all sim", String.valueOf(subscriptionInfoList.size()));
            for(int i=0;i<totalSim;i++){
                Chip chip=new Chip(getContext());
                int id=View.generateViewId();
                chip.setId(id);
                idList.add(id);
                ChipDrawable drawable= ChipDrawable.createFromAttributes(getContext(),null,0,R.style.CustomChipChoice);
                chip.setChipDrawable(drawable);
                chip.setText(subscriptionInfoList.get(i).getCarrierName());
                configurationBinding.slectionSimChipGroup.addView(chip);
            }





        }
        configurationBinding.slectionSimChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                    if(checkedId==idList.get(0)){
                        selectedSim=0;
                    }
                    else if(checkedId==idList.get(1)){
                        selectedSim=1;
                    }



            }
        });

        configurationBinding.doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingUserInput();
            }
        });

    }

    private void checkingUserInput() {
        msgText=configurationBinding.messageET.getText().toString().trim();
        notificationTitle=configurationBinding.notificationTitleET.getText().toString().trim();
        notificationDescription=configurationBinding.notificationDesET.getText().toString().trim();
        if(TextUtils.isEmpty(msgText)){
            configurationBinding.messageET.setError("This field can not be empty");
        }
        else{
            if(TextUtils.isEmpty(notificationTitle)){
                configurationBinding.notificationTitleET.setError("Title field must not be empty");
            }
            else{
                if(TextUtils.isEmpty(notificationDescription)){
                    configurationBinding.notificationDesET.setError("Description should be given");
                }
                else{
                    if(selectedSim==111){
                        Snackbar.make(configurationBinding.configurationLayout,"you must select a sim",Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        sfEditor.putString("msgText",msgText);
                        sfEditor.putString("notificationTitle",notificationTitle);
                        sfEditor.putString("notificationDescription",notificationDescription);
                        sfEditor.putBoolean("isConfigured",true);
                        sfEditor.commit();
                        navController.navigate(R.id.action_configurationFragment_to_home);
                    }
                }
            }
        }

    }
}
