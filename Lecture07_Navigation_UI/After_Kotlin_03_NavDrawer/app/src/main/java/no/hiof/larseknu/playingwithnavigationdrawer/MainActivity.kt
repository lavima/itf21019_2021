package no.hiof.larseknu.playingwithnavigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    // Setting up navigation
    private fun setupNavigation() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        // Tell that we want to use our toolbar as the actionbar
        setSupportActionBar(toolbar)

        val navigationView : NavigationView = findViewById(R.id.navigationView)

        drawerLayout = findViewById(R.id.drawer_layout)
        navController = Navigation.findNavController(this, R.id.navHostFragment)

        // We create a set with the top-level destinations
        val topLevelDestinations = HashSet<Int>()
        topLevelDestinations.add(R.id.home_dest)
        topLevelDestinations.add(R.id.collection_dest)
        topLevelDestinations.add(R.id.watchlist_dest)

        // We don't want the "Up"-navigation when navigation between top-level destination
        var configuration = AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(drawerLayout).build()

        // Sets up the toolbar together with the drawer (enables click on toolbar to show drawer)
        //NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)

        // Sets up the toolbar together with the drawer, with top-level config (enables click on toolbar to show drawer)
        NavigationUI.setupWithNavController(toolbar, navController, configuration)

        // Sets up the navController with the navigation drawer
        NavigationUI.setupWithNavController(navigationView, navController)

        // Enable our implementation for selection an item (not needed when we set the id's in menu and nav_graph to correlate)
        //navigationView.setNavigationItemSelectedListener(this)
    }

    // Handling the selection of an item in the navigation drawer (not needed when we set the id's in menu and nav_graph to correlate)
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // Sets the item to checked
        menuItem.isChecked = true

        // Close the drawer
        drawerLayout.closeDrawers()

        // Navigates to a destination based on the item pressed
        when (menuItem.itemId) {
            R.id.home_dest -> navController.navigate(R.id.home_dest)

            R.id.collection_dest -> navController.navigate(R.id.collection_dest)

            R.id.watchlist_dest -> navController.navigate(R.id.watchlist_dest)
        }

        return true

    }
}
