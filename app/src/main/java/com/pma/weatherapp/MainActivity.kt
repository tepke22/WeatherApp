package com.pma.weatherapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pma.weatherapp.base.functional.LocationTrack


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        sharedPreferences = getPreferences(MODE_PRIVATE)
        editor = sharedPreferences.edit()

        updateLocation()
    }

    private fun updateLocation() {
        if (LocationTrack(this).canGetLocation) {
            editor.putFloat("lat", LocationTrack(this).getLatitude().toFloat())
            editor.putFloat("lon", LocationTrack(this).getLongitude().toFloat())
            editor.commit()
        }
//        Log.d("TAG LAT", sharedPreferences.getFloat("lat", (-5000.0).toFloat()).toString())
//        Log.d("TAG LON", sharedPreferences.getFloat("lon", (-5000.0).toFloat()).toString())
    }
}