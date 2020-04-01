package com.example.smartassistant.View;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.Adapter.OnEventClickListener;
import com.example.smartassistant.Adapter.TimeBasedAdapter;
import com.example.smartassistant.BroadCastReciver.AlertReciever;
import com.example.smartassistant.BroadCastReciver.TimeEventReciever;
import com.example.smartassistant.BroadCastReciver.TimeOverReciever;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.DI.ViewModelFactory;
import com.example.smartassistant.databinding.FragmentTimeBasedBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeBasedFragment extends Fragment {
    TimeBasedAdapter timeBasedAdapter;
    TimeBasedEventViewModel timeBasedEventViewModel;
    RecyclerView.LayoutManager timeLayoutManager;
    NavController navController;
    List<TimeBasedEvent> timeBasedEventList;
    FragmentTimeBasedBinding timeBasedBinding;

    @Inject
    AlarmManager alarmManager;
    @Inject
    ViewModelFactory viewModelFactory;



    public TimeBasedFragment() {
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
        timeBasedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_based, container, false);
        return timeBasedBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        timeBasedEventList=new ArrayList<>();
        timeLayoutManager = new LinearLayoutManager(getContext());
        timeBasedBinding.timeListRV.setLayoutManager(timeLayoutManager);
        timeBasedEventViewModel = new ViewModelProvider(this,viewModelFactory).get(TimeBasedEventViewModel.class);
        timeBasedAdapter = new TimeBasedAdapter(timeBasedEventList);
        timeBasedBinding.timeListRV.setAdapter(timeBasedAdapter);
        timeBasedAdapter.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onEventClick(int position, View v) {
                Toast.makeText(getContext(),"time event clicked",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onEventLongClick(int position, View v) {

            }
        });

        timeBasedEventViewModel.getAllEvents().observe(getViewLifecycleOwner(), new Observer<List<TimeBasedEvent>>() {
            @Override
            public void onChanged(List<TimeBasedEvent> eventList) {

                    timeBasedEventList.clear();
                    for(TimeBasedEvent event:eventList){
                        timeBasedEventList.add(event);
                    }
                    timeBasedAdapter.notifyDataSetChanged();
                    if(timeBasedEventList.size()<=0){
                        timeBasedBinding.noItemTV.setVisibility(View.VISIBLE);
                    }
                    else {
                        timeBasedBinding.noItemTV.setVisibility(View.GONE);
                    }


            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int position=item.getGroupId();
        if(getUserVisibleHint()){
            switch(item.getItemId()) {
                case 110:
                    String id=timeBasedEventList.get(position).getId();
                    HomeFragmentDirections.ActionHomeToEditTimeEventFragment action=
                            HomeFragmentDirections.actionHomeToEditTimeEventFragment(id);
                    Log.e("id",id);
                    navController.navigate(action);
                    return true;
                case 111:
                    String eventId=timeBasedEventList.get(position).getId();
                    deleteEvent(eventId);

                    return true;

                default:
                    return true;
            }
        }
        else {
            return false;
        }


    }

    private void deleteEvent(String id) {

        //delete from alarm manager
        Intent eventIntent = new Intent(getContext(), TimeEventReciever.class);
        eventIntent.putExtra(EVENT,id);
        PendingIntent eventPendingIntent = PendingIntent.getBroadcast(getActivity(), 1, eventIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent alertIntent = new Intent(getContext(), AlertReciever.class);
        alertIntent.putExtra(EVENT,id);
        PendingIntent alertPendingIntent = PendingIntent.getBroadcast(getActivity(), 2, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent timeOverIntent = new Intent(getContext(), TimeOverReciever.class);
        timeOverIntent.putExtra(EVENT,id);
        PendingIntent timeOverPendingIntent = PendingIntent.getBroadcast(getActivity(), 3, timeOverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(eventPendingIntent);
                alarmManager.cancel(alertPendingIntent);
                alarmManager.cancel(timeOverPendingIntent);

                //delete from database
                timeBasedEventViewModel.deleteById(id);

                Toast.makeText(getContext(), "successfully deleted", Toast.LENGTH_SHORT).show();


    }


}


