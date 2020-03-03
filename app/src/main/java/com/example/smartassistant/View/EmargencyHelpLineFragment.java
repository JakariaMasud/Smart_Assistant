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

import com.example.smartassistant.Adapter.HelpLineAdapter;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentEmargencyHelpLineBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmargencyHelpLineFragment extends Fragment {
    FragmentEmargencyHelpLineBinding helpLineBinding;
    HelpLineAdapter adapter;


    public EmargencyHelpLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        helpLineBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_emargency_help_line, container, false);
        return helpLineBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[]titles=getResources().getStringArray(R.array.helplineTitle);
        String[]addresses=getResources().getStringArray(R.array.helplineAddress);
        String[]phones=getResources().getStringArray(R.array.helplinePhone);
        helpLineBinding.helplineRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new HelpLineAdapter(titles,addresses,phones);
        helpLineBinding.helplineRV.setAdapter(adapter);



    }
}
