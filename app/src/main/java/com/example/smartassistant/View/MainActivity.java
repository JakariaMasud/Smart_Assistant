package com.example.smartassistant.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.smartassistant.R;
import com.example.smartassistant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    Toolbar customToolbar;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(binding.drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(binding.navigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.configuration){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
