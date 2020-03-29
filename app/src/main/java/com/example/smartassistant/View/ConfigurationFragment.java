package com.example.smartassistant.View;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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

import javax.inject.Inject;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */
@RuntimePermissions
public class ConfigurationFragment extends Fragment {
    FragmentConfigurationBinding configurationBinding;
    NavController navController;
    int selectedSim=111;
    String msgText,notificationTitle,notificationDescription;
    @Inject
    SharedPreferences preferences;
    @Inject
    SharedPreferences.Editor sfEditor;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

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
        navController= Navigation.findNavController(view);
        ConfigurationFragmentPermissionsDispatcher.settingUpUIWithPermissionCheck(this);



    }
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    public void settingUpUI() {
        boolean hasData =preferences.contains("isConfigured");
        if(!hasData){
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Configuration Needed")
                    .setMessage("you have to set the configuration to use the App")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getContext());
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            int totalSim=subscriptionInfoList.size();
            for(int i=0;i<totalSim;i++){
                Chip chip=new Chip(getContext());
                int id=View.generateViewId();
                chip.setId(id);
                ChipDrawable drawable= ChipDrawable.createFromAttributes(getContext(),null,0,R.style.CustomChipChoice);
                chip.setChipDrawable(drawable);
                chip.setText(subscriptionInfoList.get(i).getCarrierName());
                configurationBinding.slectionSimChipGroup.addView(chip);
            }

        }
        configurationBinding.slectionSimChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                 selectedSim=checkedId;

            }
        });
        configurationBinding.doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configurationBinding.configurationProgress.setVisibility(View.VISIBLE);
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
            configurationBinding.configurationProgress.setVisibility(View.GONE);
            return;
        }
        else{
            if(TextUtils.isEmpty(notificationTitle)){
                configurationBinding.notificationTitleET.setError("Title field must not be empty");
                configurationBinding.configurationProgress.setVisibility(View.GONE);
                return;
            }
            else{
                if(TextUtils.isEmpty(notificationDescription)){
                    configurationBinding.notificationDesET.setError("Description should be given");
                    configurationBinding.configurationProgress.setVisibility(View.GONE);
                    return;
                }
                else{
                    if(selectedSim==111){
                        Snackbar.make(configurationBinding.configurationLayout,"you must select a sim",Snackbar.LENGTH_LONG).show();
                        configurationBinding.configurationProgress.setVisibility(View.GONE);
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
        Toast.makeText(getContext(),"successfully configured",Toast.LENGTH_LONG).show();
        configurationBinding.configurationProgress.setVisibility(View.GONE);
       navController.navigate(R.id.action_configuration_to_home);

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ConfigurationFragmentPermissionsDispatcher.onRequestPermissionsResult(ConfigurationFragment.this,requestCode,grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationale(final PermissionRequest request) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Permission needed")
                .setMessage("App needs the permission for its service ")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                request.proceed();
                            }
                        }
                ).show();


    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void OnDenied() {
        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onNeverAskAgain() {
        Toast.makeText(getContext(), "Never asking again", Toast.LENGTH_SHORT).show();
    }


}
