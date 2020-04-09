package com.example.smartassistant.View;


import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.smartassistant.BroadCastReciver.AlertReciever;
import com.example.smartassistant.BroadCastReciver.TimeEventReciever;
import com.example.smartassistant.BroadCastReciver.TimeOverReciever;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.DI.ViewModelFactory;
import com.example.smartassistant.databinding.FragmentAddTimeEventBinding;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.UUID;

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
public class AddTimeEventFragment extends Fragment {
    FragmentAddTimeEventBinding timeEventBinding;
    int selectedType = 999;
    Long selectedTime;
    String eventTitle;
    String eventPeriod;
    String notificationBefore;
    String selectedAmPm;
    String type;
    int notificationInt;
    int periodInt;
    NavController navController;
    public static final String EVENT = "EventData";
    TimeBasedEventViewModel timeBasedEventViewModel;

    @Inject
    AlarmManager alarmManager ;
    @Inject
    NotificationManager notificationManager ;
    @Inject
    ViewModelFactory viewModelFactory;



    public AddTimeEventFragment() {
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
        timeEventBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_time_event, container, false);
        return timeEventBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        timeBasedEventViewModel = new ViewModelProvider(this,viewModelFactory).get(TimeBasedEventViewModel.class);
        AddTimeEventFragmentPermissionsDispatcher.settingUpListenerWithPermissionCheck(this);


    }

    @NeedsPermission({Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CALL_LOG})
    public void settingUpListener() {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Permission Required")
                    .setMessage("Permission Required for to active silent Mode or to restore the general rignger Mode")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(
                                    android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                                    startActivity(intent);
                        }
                    })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(timeEventBinding.rootLayout,"you Must give the permission to access its feature",Snackbar.LENGTH_LONG).show();
                    navController.navigate(R.id.action_addTimeEventFragment_to_home);

                }
            }).show();



        }

        timeEventBinding.selectTimBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                boolean is24HourFormat = DateFormat.is24HourFormat(getContext());
                TimePickerDialog timePicker =
                        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeEventBinding.selectedTimeTV.setText("Hour:" + hourOfDay + " minute:" + minute);
                                Calendar selectedCal = Calendar.getInstance();
                                selectedCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCal.set(Calendar.MINUTE, minute);
                                selectedCal.set(Calendar.SECOND, 00);


                                if(selectedCal.getTimeInMillis()<System.currentTimeMillis()){
                                    selectedCal.add(Calendar.DATE,1);
                                }
                                selectedAmPm = (String) DateFormat.format("hh:mm a", selectedCal);
                                selectedTime = selectedCal.getTimeInMillis();
                                timeEventBinding.selectedTimeTV.setText(selectedAmPm);
                            }
                        }, hour, minute, is24HourFormat);
                timePicker.show();


            }
        });

        timeEventBinding.slectionChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.once_chip:
                        selectedType = 0;
                        break;
                    case R.id.daily_chip:
                        selectedType = 1;
                        break;
                    case R.id.weekly_chip:
                        selectedType = 2;
                        break;
                    default:
                        break;
                }

            }
        });

        timeEventBinding.timeAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEventBinding.timeProgress.setVisibility(ProgressBar.VISIBLE);
                eventTitle = timeEventBinding.timeTitleET.getText().toString();
                eventPeriod = timeEventBinding.eventPeriodET.getText().toString();
                notificationBefore = timeEventBinding.notificationET.getText().toString();


                if (TextUtils.isEmpty(eventTitle)) {
                    timeEventBinding.timeTitleET.setError("Title field can not be empty");
                    timeEventBinding.timeProgress.setVisibility(ProgressBar.GONE);
                    return;
                } else {
                    if (selectedTime == null) {
                        Snackbar.make(timeEventBinding.rootLayout, "Time must be selected", Snackbar.LENGTH_LONG).show();
                        timeEventBinding.timeProgress.setVisibility(ProgressBar.GONE);
                        return;
                    } else {
                        if (selectedType == 999) {
                            Snackbar.make(timeEventBinding.rootLayout, "please select the type of action", Snackbar.LENGTH_LONG).show();
                            timeEventBinding.timeProgress.setVisibility(ProgressBar.GONE);
                            return;
                        } else {
                            if (TextUtils.isEmpty(eventPeriod)) {
                                timeEventBinding.eventPeriodET.setError("period of the event must be given");
                                timeEventBinding.timeProgress.setVisibility(ProgressBar.GONE);
                                return;
                            } else {
                                if (TextUtils.isEmpty(notificationBefore)) {
                                    timeEventBinding.notificationET.setError("please select notification Before time");
                                    timeEventBinding.timeProgress.setVisibility(ProgressBar.GONE);
                                    return;
                                } else {
                                    addToTheDatabase();
                                }
                            }
                        }
                    }
                }


            }
        });

    }


    public void addToAlarmManager(String id) {
        Intent eventIntent = new Intent(getContext(), TimeEventReciever.class);
        eventIntent.putExtra(EVENT,id);
        PendingIntent eventPendingIntent = PendingIntent.getBroadcast(getActivity(), 1, eventIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent alertIntent = new Intent(getContext(), AlertReciever.class);
        alertIntent.putExtra(EVENT,id);
        PendingIntent alertPendingIntent = PendingIntent.getBroadcast(getActivity(), 2, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent timeOverIntent = new Intent(getContext(), TimeOverReciever.class);
        timeOverIntent.putExtra(EVENT,id);
        PendingIntent timeOverPendingIntent = PendingIntent.getBroadcast(getActivity(), 3, timeOverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (selectedType == 0) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime.longValue(), eventPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime.longValue() - notificationInt * 60 * 1000, alertPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime.longValue() + periodInt * 60 * 1000, timeOverPendingIntent);
            timeEventBinding.timeProgress.setVisibility(View.GONE);
            navController.navigate(R.id.action_addTimeEventFragment_to_home);

        } else if (selectedType == 1) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue(), AlarmManager.INTERVAL_DAY, eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue() - notificationInt * 60 * 1000, AlarmManager.INTERVAL_DAY, alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue() + periodInt * 60 * 1000, AlarmManager.INTERVAL_DAY, timeOverPendingIntent);
            timeEventBinding.timeProgress.setVisibility(View.GONE);
            navController.navigate(R.id.action_addTimeEventFragment_to_home);

        } else if (selectedType == 2) {

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue(), AlarmManager.INTERVAL_DAY * 7, eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue() - notificationInt * 60 * 1000, AlarmManager.INTERVAL_DAY * 7, alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime.longValue() + periodInt * 60 * 1000, AlarmManager.INTERVAL_DAY * 7, timeOverPendingIntent);
            timeEventBinding.timeProgress.setVisibility(View.GONE);
            navController.navigate(R.id.action_addTimeEventFragment_to_home);

        }

    }

    private void addToTheDatabase() {
        switch (selectedType){
            case 0:
                type = "Once";
                break;
            case 1:
                type = "Daily";
                break;
            case 2:
                type = "Weekly";
                break;
                default:
                    break;
        }
        notificationInt = Integer.parseInt(notificationBefore.trim());
        periodInt = Integer.parseInt(eventPeriod.trim());
        String id= UUID.randomUUID().toString();
        TimeBasedEvent event = new TimeBasedEvent(id,eventTitle, type, periodInt, selectedTime, notificationInt, selectedAmPm);
        timeBasedEventViewModel.insert(event);
            addToAlarmManager(id);



    }



    @OnShowRationale({Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CALL_LOG})
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

    @OnPermissionDenied({Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CALL_LOG})
    void OnDenied() {
        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CALL_LOG})
    void onNeverAskAgain() {
        Toast.makeText(getContext(), "Never asking again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddTimeEventFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}



