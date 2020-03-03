package com.example.smartassistant.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.SingleHelplineBinding;

public class HelpLineAdapter extends RecyclerView.Adapter<HelpLineAdapter.HelpLineViewHolder> {
String[]titleList;
String[]addressList;
String[]phoneList;

    public HelpLineAdapter(final String[] titleList, final String[] addressList, final String[] phoneList) {
        this.titleList = titleList;
        this.addressList = addressList;
        this.phoneList = phoneList;
    }

    @NonNull
    @Override
    public HelpLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        SingleHelplineBinding binding= DataBindingUtil.inflate(inflater, R.layout.single_helpline,parent,false);
        return new HelpLineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpLineViewHolder holder, int position) {
        holder.helplineBinding.helplineTitleTV.setText(titleList[position]);
        holder.helplineBinding.helplineAddressTV.setText(addressList[position]);
        holder.helplineBinding.helplinePhoneTV.setText(phoneList[position]);

    }

    @Override
    public int getItemCount() {
        return titleList.length;
    }

    public class HelpLineViewHolder extends RecyclerView.ViewHolder {
        SingleHelplineBinding helplineBinding;
        public HelpLineViewHolder(@NonNull SingleHelplineBinding itemView) {
            super(itemView.getRoot());
            helplineBinding=itemView;
        }
    }
}
