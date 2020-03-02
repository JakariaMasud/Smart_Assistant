package com.example.smartassistant.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.SingleLocationEventBinding;

import java.util.ArrayList;
import java.util.List;

public class LocationBasedAdapter extends RecyclerView.Adapter<LocationBasedAdapter.LocationBasedViewHolder> {
    List<LocationBasedEvent> eventList=new ArrayList<>();

    public LocationBasedAdapter(final List<LocationBasedEvent> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public LocationBasedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        SingleLocationEventBinding binding= DataBindingUtil.inflate(inflater, R.layout.single_location_event,parent,false);
        return new LocationBasedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationBasedViewHolder holder, int position) {
        LocationBasedEvent event=eventList.get(position);
        holder.singleLocationEventBinding.locationTitleTV.setText(event.getTitle());
        holder.singleLocationEventBinding.addressTV.setText("elected Location:  "+event.getAddress());
        holder.singleLocationEventBinding.areaTV.setText("area to be covered :"+event.getRadiusInMeter()+" meter");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class LocationBasedViewHolder extends RecyclerView.ViewHolder {
        SingleLocationEventBinding singleLocationEventBinding;
        public LocationBasedViewHolder(@NonNull SingleLocationEventBinding itemView) {
            super(itemView.getRoot());
            singleLocationEventBinding=itemView;
        }
    }
}
