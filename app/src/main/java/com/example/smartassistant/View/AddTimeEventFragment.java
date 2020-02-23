package com.example.smartassistant.View;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentAddTimeEventBinding;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.roger.catloadinglibrary.CatLoadingView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTimeEventFragment extends Fragment  {
    FragmentAddTimeEventBinding timeEventBinding;
    int selectedType=999;
    Long selectedTime;
    String eventTitle;
    String eventPeriod;
    String notificationBefore;
    String selectedAmPm;
    String type;
    TimeBasedEventViewModel timeBasedEventViewModel;
    int notificationInt;
    int periodInt;



    public AddTimeEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timeEventBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_time_event, container, false);
        return timeEventBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeBasedEventViewModel=new ViewModelProvider(this).get(TimeBasedEventViewModel.class);
        timeEventBinding.selectTimBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                boolean is24HourFormat= DateFormat.is24HourFormat(getContext());
                TimePickerDialog timePicker=
                        new TimePickerDialog(getContext(),listener,hour,minute,is24HourFormat);
                timePicker.show();


            }
        });
        timeEventBinding.slectionChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(ChipGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.once_chip:
                       selectedType=0;
                       break;
                   case R.id.daily_chip:
                       selectedType=1;
                       break;
                   case R.id.weekly_chip:
                       selectedType=2;
                       break;
                   default:
                       break;
               }

           }
       });


        timeEventBinding.timeAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventTitle=timeEventBinding.timeTitleET.getText().toString();
                eventPeriod=timeEventBinding.eventPeriodET.getText().toString();
                notificationBefore=timeEventBinding.notificationET.getText().toString();


                if(TextUtils.isEmpty(eventTitle)){
                    timeEventBinding.timeTitleET.setError("Title field can not be empty");
                }
                else {
                    if (selectedTime == null) {
                        Snackbar.make(timeEventBinding.rootLayout,"Time must be selected",Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        if(selectedType==999){
                            Snackbar.make(timeEventBinding.rootLayout,"please select the type of action",Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            if(TextUtils.isEmpty(eventPeriod)){
                                timeEventBinding.eventPeriodET.setError("period of the event must be given");
                                return;
                            }
                            else {
                                if(TextUtils.isEmpty(notificationBefore)){
                                    timeEventBinding.notificationET.setError("please select notification Before time");
                                    return;
                                }
                                else {
                                    timeEventBinding.progressCircularBar.show();
                                    addToAlarmManager();
                                }
                            }
                        }
                    }
                }


            }
        });
    }

    private void addToAlarmManager() {
        AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(getContext().ALARM_SERVICE);
        Intent eventIntent=new Intent(getContext(),TimeEventReciever.class);
        PendingIntent eventPendingIntent=PendingIntent.getBroadcast(getContext(),1,eventIntent,0);
        Intent alertIntent= new Intent(getContext(),AlertReciever.class);
        PendingIntent alertPendingIntent=PendingIntent.getBroadcast(getContext(),2,alertIntent,0);
        Intent timeOverIntent=new Intent(getContext(),TimeOverReciever.class);
        PendingIntent timeOverPendingIntent=PendingIntent.getBroadcast(getContext(),3,timeOverIntent,0);
        notificationInt=Integer.parseInt(notificationBefore.trim());
        periodInt=Integer.parseInt(eventPeriod.trim());
        if(selectedType==0){
            type="Once";
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue(),eventPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue()-notificationInt*60*1000,alertPendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue()+periodInt*60*1000,timeOverPendingIntent);
            addToTheDatabase();
        }
        else if(selectedType==1){
            type="Daily";
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue(),AlarmManager.INTERVAL_DAY,eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue()-notificationInt*60*1000,AlarmManager.INTERVAL_DAY,alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue()+periodInt*60*1000,AlarmManager.INTERVAL_DAY,timeOverPendingIntent);
            addToTheDatabase();

        }
        else if(selectedType==2){
            type="Weekly";
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue(),AlarmManager.INTERVAL_DAY*7,eventPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue()-notificationInt*60*1000,AlarmManager.INTERVAL_DAY*7,alertPendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectedTime.longValue()+periodInt*60*1000,AlarmManager.INTERVAL_DAY*7,timeOverPendingIntent);
            addToTheDatabase();
        }


    }

    private void addToTheDatabase() {

        TimeBasedEvent event=new TimeBasedEvent(eventTitle,type,periodInt,selectedTime,notificationInt,selectedAmPm);
       long rowId= timeBasedEventViewModel.insert(event);
       String id=String.valueOf(rowId);
       if(TextUtils.isEmpty(id)){
           timeEventBinding.progressCircularBar.hide();
           Snackbar.make(timeEventBinding.rootLayout,"Something Went Wrong",Snackbar.LENGTH_LONG).show();

       }




    }

    TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
     @Override
     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
         timeEventBinding.selectedTimeTV.setText("Hour:"+hourOfDay+" minute:"+minute);
         Calendar selectedCal=Calendar.getInstance();
         selectedCal.set(Calendar.HOUR_OF_DAY,hourOfDay);
         selectedCal.set(Calendar.MINUTE,minute);
         selectedTime=selectedCal.getTimeInMillis();
         selectedAmPm= (String) DateFormat.format("hh:mm a",selectedCal);
         timeEventBinding.selectedTimeTV.setText(selectedAmPm);

     }
 };
}
