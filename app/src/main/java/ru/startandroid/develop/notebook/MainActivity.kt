package ru.startandroid.develop.notebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.notebook.databinding.ActivityMainBinding
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.sharedpreferences.SharedPreferencesKeys.USER_SELECTED_THEME_MODE_KEY
import ru.startandroid.develop.notebook.utils.AppThemeModes
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preference: PreferenceHelper

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = binding.mainToolbar

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

        val appThemeMode = preference.getString(USER_SELECTED_THEME_MODE_KEY)
        if (appThemeMode != null) setupUserSavedAppMode(appThemeMode)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupUserSavedAppMode(mode: String) {
        when (mode) {
            AppThemeModes.DARK_MODE.name -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppThemeModes.LIGHT_MODE.name -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppThemeModes.SYSTEM_ADAPT.name -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        }
    }
}