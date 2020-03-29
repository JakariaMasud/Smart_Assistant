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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentEditConfigurationBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditConfigurationFragment extends Fragment {
    FragmentEditConfigurationBinding editConfigurationBinding;
    NavController navController;
    int selectedSim=111;
    String msgText,notificationTitle,notificationDescription;
    List<Integer> idList;
    @Inject
    SharedPreferences preferences;
    @Inject
    SharedPreferences.Editor sfEditor;




    public EditConfigurationFragment() {
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
        editConfigurationBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_configuration, container, false);
        return editConfigurationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idList=new ArrayList<>();
        navController= Navigation.findNavController(view);
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
            editConfigurationBinding.editNotificationDesET.setText(notificationDescription);
            if(selectedSim!=111){
                editConfigurationBinding.editSlectionSimChipGroup.check(selectedSim);
            }

        }
        editConfigurationBinding.editSlectionSimChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
            selectedSim=checkedId;

            }
        });
        editConfigurationBinding.editDoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editConfigurationBinding.editProgress.setVisibility(View.VISIBLE);
                checkingUserInput();
            }
        });

    }
    private void checkingUserInput() {
        msgText=editConfigurationBinding.editMessageET.getText().toString().trim();
        notificationTitle=editConfigurationBinding.editNotificationTitleET.getText().toString().trim();
        notificationDescription=editConfigurationBinding.editNotificationDesET.getText().toString().trim();
        if(TextUtils.isEmpty(msgText)){
            editConfigurationBinding.editMessageET.setError("This field can not be empty");
            editConfigurationBinding.editProgress.setVisibility(View.GONE);
            return;
        }
        else{
            if(TextUtils.isEmpty(notificationTitle)){
                editConfigurationBinding.editNotificationTitleET.setError("Title field must not be empty");
                editConfigurationBinding.editProgress.setVisibility(View.GONE);
                return;
            }
            else{
                if(TextUtils.isEmpty(notificationDescription)){
                    editConfigurationBinding.editNotificationDesET.setError("Description should be given");
                    editConfigurationBinding.editProgress.setVisibility(View.GONE);
                    return;
                }
                else{
                    if(selectedSim==111){
                        Snackbar.make(editConfigurationBinding.editconfigurationLayout,"you must select a sim",Snackbar.LENGTH_LONG).show();
                        editConfigurationBinding.editProgress.setVisibility(View.GONE);
                        return;
                    }
                    else {
                        saveTheData();
                    }
                }
            }
        }

    }

    private void saveTheData() {
        sfEditor.putString("msgText",msgText);
        sfEditor.putString("notificationTitle",notificationTitle);
        sfEditor.putString("notificationDescription",notificationDescription);
        sfEditor.putBoolean("isConfigured",true);
        sfEditor.putInt("selectedSim",selectedSim);
        sfEditor.commit();
        Toast.makeText(getContext(),"successfully configuration Updated",Toast.LENGTH_LONG).show();
        editConfigurationBinding.editProgress.setVisibility(View.GONE);
        navController.navigate(EditConfigurationFragmentDirections.actionEditConfigurationToHome());

    }
}
