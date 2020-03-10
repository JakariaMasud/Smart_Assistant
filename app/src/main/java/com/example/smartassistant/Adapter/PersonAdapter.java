package com.example.smartassistant.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.SinglePersonBinding;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    String[]nameList;
    String[]idList;
    String[]contactList;
    String[]emailList;
    int[]picList;

    public PersonAdapter(final String[] nameList, final String[] idList, final String[] contactList, final String[] emailList, final int[] picList) {
        this.nameList = nameList;
        this.idList = idList;
        this.contactList = contactList;
        this.emailList = emailList;
        this.picList = picList;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        SinglePersonBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.single_person,parent,false);

        return new PersonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.personBinding.personIV.setImageResource(picList[position]);
        holder.personBinding.idTV.setText(idList[position]);
        holder.personBinding.nameTV.setText(nameList[position]);
        holder.personBinding.contactTV.setText("Contact Number : "+contactList[position]);
        holder.personBinding.emailTV.setText("Email: "+emailList[position]);
        holder.personBinding.deptTV.setText("Dept:Computer Science and Engineering(CSE)");

    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        SinglePersonBinding personBinding;
        public PersonViewHolder(@NonNull SinglePersonBinding itemView) {
            super(itemView.getRoot());
            personBinding=itemView;
        }
    }
}
