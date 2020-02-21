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

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentAddTimeEventBinding;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTimeEventFragment extends Fragment  {
    FragmentAddTimeEventBinding timeEventBinding;
    int selectedType;
    Long selectedTime;
    String eventTitle;
    String eventPeriod;
    String notificationBefore;


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
        //timeEventBinding.actionTypeSp.setItems( "Once","Daily","Weekly");
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
/*        timeEventBinding.actionTypeSp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch(position){
                    case 0:
                        selectedType=0;
                        break;
                    case 1:
                        selectedType=1;
                        break;
                    case 2:
                        selectedType=2;
                        break;
                        default:
                            selectedType=0;
                            break;

                }
            }
        });*/

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
                        //snakbar
                    }
                    else{
                        if(selectedType==999){
                            //timeEventBinding.actionTypeSp.setError("please select a action type");
                        }
                        else {
                            if(TextUtils.isEmpty(eventPeriod)){
                                timeEventBinding.eventPeriodET.setError("period of the event must be given");
                            }
                            else {
                                if(TextUtils.isEmpty(notificationBefore)){
                                    timeEventBinding.notificationET.setError("please select notification Before time");
                                }
                                else {
                                    Toast.makeText(getContext(), "successfully added", Toast.LENGTH_SHORT).show();
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
        if(selectedType==0){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue(),eventPendingIntent);
            Intent alertIntent= new Intent(getContext(),AlertReciever.class);
            PendingIntent alertPendingIntent=PendingIntent.getBroadcast(getContext(),2,alertIntent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue()-1*60*1000,alertPendingIntent);
            Intent timeOverIntent=new Intent(getContext(),TimeOverReciever.class);
            PendingIntent timeOverPendingIntent=PendingIntent.getBroadcast(getContext(),3,timeOverIntent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,selectedTime.longValue()+Integer.parseInt(eventPeriod.trim())*60*1000,timeOverPendingIntent);
            Toast.makeText(getContext(), "alamrm added", Toast.LENGTH_SHORT).show();
        }
        else if(selectedType==1){

        }
        else if(selectedType==2){

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
     }
 };
}
