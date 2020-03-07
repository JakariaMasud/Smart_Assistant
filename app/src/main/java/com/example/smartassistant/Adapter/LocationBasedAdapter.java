package com.example.smartassistant.Adapter;

import android.view.ContextMenu;
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
    OnEventClickListener listener;

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

    public class LocationBasedViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        SingleLocationEventBinding singleLocationEventBinding;
        public LocationBasedViewHolder(@NonNull SingleLocationEventBinding itemView) {
            super(itemView.getRoot());
            singleLocationEventBinding=itemView;
            singleLocationEventBinding.getRoot().setOnCreateContextMenuListener(this);
            singleLocationEventBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),210,0,"Edit");
            contextMenu.add(getAdapterPosition(),211,0,"Delete");
        }
    }
    public void setOnEventClickListener(OnEventClickListener listener){
        this.listener=listener;
    }
}
