package com.example.smartassistant.Adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.SingleTimeEventBinding;
import java.util.ArrayList;
import java.util.List;

public class TimeBasedAdapter extends RecyclerView.Adapter<TimeBasedAdapter.TimeBasedViewHolder> {
    List<TimeBasedEvent> eventList=new ArrayList<>();
    OnEventClickListener listener;

    public TimeBasedAdapter(final List<TimeBasedEvent> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public TimeBasedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        SingleTimeEventBinding binding= DataBindingUtil.inflate(inflater, R.layout.single_time_event,parent,false);
        return new TimeBasedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeBasedViewHolder holder, int position) {
        TimeBasedEvent event=eventList.get(position);
        holder.singleTimeEventBinding.titleTV.setText(event.getTitle());
        holder.singleTimeEventBinding.timeTV.setText("selected time :"+event.getTimeAmPm());
        holder.singleTimeEventBinding.periodTV.setText("event period :"+event.getPeriod()+"minutes");
        holder.singleTimeEventBinding.typeTV.setText("event type : "+event.getType());
        holder.singleTimeEventBinding.notificationTV.setText("notification before :"+String.valueOf(event.getNotificationBefore())+" minutes");

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class TimeBasedViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        SingleTimeEventBinding singleTimeEventBinding;
        public TimeBasedViewHolder(@NonNull SingleTimeEventBinding itemView) {
            super(itemView.getRoot());
            singleTimeEventBinding=itemView;
            singleTimeEventBinding.getRoot().setOnCreateContextMenuListener(this);
            singleTimeEventBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onEventClick(getAdapterPosition(),view);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(),110,0,"Edit");
            contextMenu.add(this.getAdapterPosition(),111,0,"Delete");
        }


    }
    public void setOnEventClickListener(OnEventClickListener listener){
        this.listener=listener;
    }
}
