package com.example.smartassistant.View;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartassistant.Adapter.PersonAdapter;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentAboutUsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    FragmentAboutUsBinding binding;
    String[]nameList;
    String[]idList;
    String[]contactList;
    String[]emailList;
    int[]picList;
    PersonAdapter adapter;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_about_us, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.personRV.setLayoutManager(new LinearLayoutManager(getContext()));
        nameList=getResources().getStringArray(R.array.nameList);
        idList=getResources().getStringArray(R.array.idList);
        contactList=getResources().getStringArray(R.array.contactList);
        emailList=getResources().getStringArray(R.array.emailList);
        picList=new int []{R.drawable.robin,R.drawable.mehedi,R.drawable.arifur,R.drawable.teacher};
        adapter=new PersonAdapter(nameList,idList,contactList,emailList,picList);
        binding.personRV.setAdapter(adapter);


    }
}
