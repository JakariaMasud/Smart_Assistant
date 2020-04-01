package com.example.smartassistant.View;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartassistant.BroadCastReciver.AlertReciever;
import com.example.smartassistant.BroadCastReciver.TimeEventReciever;
import com.example.smartassistant.BroadCastReciver.TimeOverReciever;
import com.example.smartassistant.DI.ViewModelFactory;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentEditTimeEventBinding;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import javax.inject.Inject;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTimeEventFragment extends Fragment {
    FragmentEditTimeEventBinding editTimeEventBinding;
    String eventId;
    NavController navController;
    TimeBasedEventViewModel timeBasedEventViewModel;
    private String title;
    private String type;
    private int period;
    private long selectedTime;
    private  int notificationBefore;
    private String timeAmPm;
    TimeBasedEvent event;
    @Inject
    AlarmManager alarmManager;
    @Inject
    ViewModelFactory viewModelFactory;


    public EditTimeEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("injection","doing injection");
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editTimeEventBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_time_event, container, false);
        return editTimeEventBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        timeBasedEventViewModel=new ViewModelProvider(this,viewModelFactory).get(TimeBasedEventViewModel.class);
        if(getArguments()!=null){
            eventId= EditLocationEventFragmentArgs.fromBundle(getArguments()).getEventId();
            Log.e("event id",eventId);
            settingUpUi();
        }
    }

    private void settingUpUi() {
        event=timeBasedEventViewModel.getById(eventId);
        title=event.getTitle();
        period=event.getPeriod();
        timeAmPm=event.getTimeAmPm();
        type=event.getType();
        selectedTime=event.getSelectedTime();
        notificationBefore=event.getNotificationBefore();
        editTimeEventBinding.editTimeTitleET.setText(title);
        editTimeEventBinding.editEventPeriodET.setText(String.valueOf(period));
        editTimeEventBinding.editSelectedTimeTV.setText(timeAmPm);
        editTimeEventBinding.editNotificationET.setText(String.valueOf(notificationBefore));
        if (type.equals("Once")){
            editTimeEventBinding.editSlectionChipGroup.check(editTimeEventBinding.editOnceChip.getId());
        }
        else if(type.equals("Daily")){
            editTimeEventBinding.editSlectionChipGroup.check(editTimeEventBinding.editDailyChip.getId());
        }
        else if(type.equals("Weekly")){
            editTimeEventBinding.editSlectionChipGroup.check(editTimeEventBinding.editWeeklyChip.getId());
        }
        editTimeEventBinding.editSelectTimBTN.setOnClickListener(new View.OnClickListener() {
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
                                editTimeEventBinding.editSelectedTimeTV.setText("Hour:" + hourOfDay + " minute:" + minute);
                                Calendar selectedCal = Calendar.getInstance();
                                selectedCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCal.set(Calendar.MINUTE, minute);
                                if(selectedCal.getTimeInMillis()<System.currentTimeMillis()){
                                    selectedCal.add(Calendar.DATE,1);
                                }
                                selectedTime = selectedCal.getTimeInMillis();
                                timeAmPm = (String) DateFormat.format("hh:mm a", selectedCal);
                                editTimeEventBinding.editSelectedTimeTV.setText(timeAmPm);
                            }
                        }, hour, minute, is24HourFormat);
                timePicker.show();


            }
        });

        editTimeEventBinding.editSlectionChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.edit_once_chip:
                        type="Once";
                        break;
                    case R.id.edit_daily_chip:
                        type="Daily";
                        break;
                    case R.id.edit_weekly_chip:
                        type="Weekly";
                        break;

                }
            }
        });
        editTimeEventBinding.editTimeAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTimeEventBinding.edittimeProgress.setVisibility(View.VISIBLE);
                title=editTimeEventBinding.editTimeTitleET.getText().toString().trim();
                period=Integer.parseInt(editTimeEventBinding.editEventPeriodET.getText().toString().trim());
                notificationBefore=Integer.parseInt(editTimeEventBinding.editNotificationET.getText().toString().trim());
                if(TextUtils.isEmpty(title)){
                    editTimeEventBinding.editTimeTitleET.setError("title must be given");
                    editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
                    return;
                }
                else {
                    if (period == 0) {
                        editTimeEventBinding.editEventPeriodET.setError("you must give a value of period in minute");
                        editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
                        return;
                    }
                    else {
                        if(TextUtils.isEmpty(timeAmPm)){
                            Snackbar.make(editTimeEventBinding.editTimeLayout,"you must select a time",Snackbar.LENGTH_LONG).show();
                            editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
                            return;

                        }
                        else{
                            if(TextUtils.isEmpty(type)){
                                Snackbar.make(editTimeEventBinding.editTimeLayout,"you must select a Type of action",Snackbar.LENGTH_LONG).show();
                                editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
                                return;
                            }
                            else{
                                if(notificationBefore==0){
                                    editTimeEventBinding.editNotificationET.setError("this field can not be empty");
                                    editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
                                    return;
                                }
                                else{
                                    updateEvent();
                                }
                            }

                        }
                    }
                }




            }
        });



    }

    private void updateEvent() {
        //cancel from alarm manager
        Intent eventIntent = new Intent(getContext(), TimeEventReciever.class);
        eventIntent.putExtra(EVENT,eventId);
        PendingIntent eventPendingIntent = PendingIntent.getBroadcast(getActivity(), 1, eventIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent alertIntent = new Intent(getContext(), AlertReciever.class);
        alertIntent.putExtra(EVENT,eventId);
        PendingIntent alertPendingIntent = PendingIntent.getBroadcast(getActivity(), 2, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent timeOverIntent = new Intent(getContext(), TimeOverReciever.class);
        timeOverIntent.putExtra(EVENT,eventId);
        PendingIntent timeOverPendingIntent = PendingIntent.getBroadcast(getActivity(), 3, timeOverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(eventPendingIntent);
        alarmManager.cancel(alertPendingIntent);
        alarmManager.cancel(timeOverPendingIntent);
        //create updated event
        TimeBasedEvent timeBasedEvent=new TimeBasedEvent(eventId,title,type,period,selectedTime,notificationBefore,timeAmPm);
        //set the alarm manager again
        if (type.equals("Once")) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime, eventPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime - notificationBefore * 60 * 1000, alertPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime + period * 60 * 1000, timeOverPendingIntent);

        } else if (type.equals("Daily")) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime, AlarmManager.INTERVAL_DAY, eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime - notificationBefore * 60 * 1000, AlarmManager.INTERVAL_DAY, alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime + period * 60 * 1000, AlarmManager.INTERVAL_DAY, timeOverPendingIntent);


        } else if (type.equals("Weekly")) {

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime, AlarmManager.INTERVAL_DAY * 7, eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime - notificationBefore * 60 * 1000, AlarmManager.INTERVAL_DAY * 7, alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectedTime + period * 60 * 1000, AlarmManager.INTERVAL_DAY * 7, timeOverPendingIntent);
        }
        //update the database
        timeBasedEventViewModel.update(timeBasedEvent);
        Toast.makeText(getContext(), "Successfully updated the event", Toast.LENGTH_SHORT).show();
        editTimeEventBinding.edittimeProgress.setVisibility(View.GONE);
        navController.navigate(EditTimeEventFragmentDirections.actionEditTimeEventFragmentToHome());
    }
}
