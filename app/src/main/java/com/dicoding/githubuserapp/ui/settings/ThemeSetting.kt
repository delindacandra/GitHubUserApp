package com.dicoding.githubuserapp.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.githubuserapp.databinding.ActivityThemeSettingBinding
import com.dicoding.githubuserapp.ui.ViewModelFactory

class ThemeSetting : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchTheme.setOnCheckedChangeListener  { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

       // val pref = SettingPreferences.getInstance(application.dataStore)
        //val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val themeViewModel: ThemeViewModel by viewModels { factory }

        themeViewModel.getTheme().observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = isDarkModeActive
        }
        
        binding.switchTheme.setOnCheckedChangeListener {_: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveTheme(isChecked)  }
    }
}