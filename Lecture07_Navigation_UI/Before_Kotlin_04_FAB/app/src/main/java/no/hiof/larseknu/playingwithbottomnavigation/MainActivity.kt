package no.hiof.larseknu.playingwithbottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gets a reference to the navController
        var navController = findNavController(R.id.navHostFragment)

        // Sets up bottom navigation
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        // Gets a reference to the bottom navigation view
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Sets it up with the navigationcontroller (and navigation graph)
        bottomNav?.setupWithNavController(navController)

        // If the id in the menu and navigation graph are the same, this is done automagically
        /*bottomNav.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.home_destination -> navController.navigate(R.id.home_dest)
                R.id.search_destination -> navController.navigate(R.id.search_dest)
                R.id.collection_destination -> navController.navigate(R.id.collection_dest)
            }

            true
        }*/
    }
}
