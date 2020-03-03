package com.example.smartassistant.View;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartassistant.Adapter.OnEventClickListener;
import com.example.smartassistant.Adapter.TimeBasedAdapter;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;
import com.example.smartassistant.databinding.FragmentTimeBasedBinding;

import java.util.ArrayList;
import java.util.List;

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


    public TimeBasedFragment() {
        // Required empty public constructor
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
        timeBasedEventList=new ArrayList<>();
        timeLayoutManager = new LinearLayoutManager(getContext());
        timeBasedBinding.timeListRV.setLayoutManager(timeLayoutManager);
        timeBasedEventViewModel = new ViewModelProvider(this).get(TimeBasedEventViewModel.class);
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

        timeBasedEventViewModel.getAllEvents().observe(this, new Observer<List<TimeBasedEvent>>() {
            @Override
            public void onChanged(List<TimeBasedEvent> eventList) {
                timeBasedEventList.addAll(eventList);
                if ( timeBasedEventList.size()> 0) {
                    timeBasedBinding.noItemTV.setVisibility(View.GONE);
                    timeBasedAdapter.notifyDataSetChanged();

                } else {
                    timeBasedBinding.noItemTV.setVisibility(View.VISIBLE);
                }



            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int position=item.getGroupId();

        switch(item.getItemId()) {
            case 110:
                Toast.makeText(getContext(),"edit time",Toast.LENGTH_LONG).show();

                return true;
            case 111:
                Toast.makeText(getContext(),"delete time",Toast.LENGTH_LONG).show();

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}


