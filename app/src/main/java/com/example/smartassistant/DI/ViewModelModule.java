package com.example.smartassistant.DI;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartassistant.ViewModel.LocationBasedEventViewModel;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
    @Binds
    @IntoMap
    @ViewModelKey(LocationBasedEventViewModel.class)
    abstract ViewModel LocationBasedViewModel(LocationBasedEventViewModel locationBasedEventViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TimeBasedEventViewModel.class)
    abstract ViewModel TimeBasedViewModel(TimeBasedEventViewModel timeBasedEventViewModel);

}
