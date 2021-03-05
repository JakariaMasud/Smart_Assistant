package com.example.smartassistant.View;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentAboutMeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMeFragment extends Fragment {
    FragmentAboutMeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_about_me, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.personIV.setImageResource(R.drawable.jakaria);
        binding.nameTV.setText("Jakaria Masud");
        binding.designationTV.setText("Android Developer");
        binding.phoneTV.setText("Phone : +8801737196677");
        binding.emailTV.setText("Email :  jakariamasudliton@gmail.com");


    }
}
