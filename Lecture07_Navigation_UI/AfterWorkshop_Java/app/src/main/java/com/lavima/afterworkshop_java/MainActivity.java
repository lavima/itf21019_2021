package com.lavima.afterworkshop_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private NavController controller;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find navigation controller in the navigation host
        controller = Navigation.findNavController(this, R.id.fragment);

        // Find drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);

        // The following activates the bottom navigation menu. The menu item ids match destination ids so no explicit
        // handling is necessary

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Connect with navigation controller for automatic navigation
        //NavigationUI.setupWithNavController(bottomNavigation, controller);
        // Simple explicit navigation
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                controller.navigate(menuItem.getItemId());
                menuItem.setChecked(true);
                return true;
            }
        });

        // This will enable us to click the toolbar/appbar to open drawer

        Toolbar toolbar = findViewById(R.id.toolbar);
        // This will make sure that we don't get a back/up-arrow on the top level destinations
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.collectionFragment)
                .setOpenableLayout(drawerLayout).build();
        NavigationUI.setupWithNavController(toolbar, controller, configuration);


        // The following activates the navigation drawer.

        NavigationView drawerView = findViewById(R.id.navigation_view);
        // Connect with navigation controller for automatic navigation (menu ids must match destination ids)
        NavigationUI.setupWithNavController(drawerView, controller);
        // Simple explicit navigation
        drawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                controller.navigate(menuItem.getItemId());
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
}