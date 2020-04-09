package com.example.smartassistant.DI;

import com.example.smartassistant.View.AddLocationEventFragment;
import com.example.smartassistant.View.AddTimeEventFragment;
import com.example.smartassistant.View.ConfigurationFragment;
import com.example.smartassistant.View.EditConfigurationFragment;
import com.example.smartassistant.View.EditLocationEventFragment;
import com.example.smartassistant.View.EditTimeEventFragment;
import com.example.smartassistant.View.HomeFragment;
import com.example.smartassistant.View.LocationBasedFragment;
import com.example.smartassistant.View.TimeBasedFragment;
import com.example.smartassistant.View.ViewAllFragment;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component (modules = {AppModule.class,DataBaseModule.class,ViewModelModule.class,ServiceModule.class,WorkerModule.class})
public interface ApplicationComponent {
    void inject(AddLocationEventFragment addLocationEventFragment);
    void inject(AddTimeEventFragment addTimeEventFragment);
    void inject(HomeFragment homeFragment);
    void inject(TimeBasedFragment timeBasedFragment);
    void inject(LocationBasedFragment locationBasedFragment);

    void inject(EditTimeEventFragment editTimeEventFragment);

    void inject(EditLocationEventFragment editLocationEventFragment);

    void inject(EditConfigurationFragment editConfigurationFragment);

    void inject(ConfigurationFragment configurationFragment);
    WorkerFactory factory();

    void inject(ViewAllFragment viewAllFragment);
}
