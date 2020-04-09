package com.example.smartassistant.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.smartassistant.Adapter.EventFragmentAdapter;
import com.example.smartassistant.R;
import com.example.smartassistant.databinding.FragmentViewAllBinding;

import javax.inject.Inject;

import static androidx.databinding.DataBindingUtil.inflate;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAllFragment extends Fragment {
    FragmentViewAllBinding binding;
    NavController navController;
    EventFragmentAdapter adapter;
    @Inject
    SharedPreferences preferences;

    public ViewAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_all, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        adapter=new EventFragmentAdapter(getChildFragmentManager());
        binding.eventViewPager.setAdapter(adapter);
        binding.eventTabLayout.setupWithViewPager(binding.eventViewPager);
    }
}
