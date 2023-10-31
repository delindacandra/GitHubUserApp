package com.dicoding.githubuserapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuserapp.data.local.database.Repository
import kotlinx.coroutines.launch

class ThemeViewModel(private val repository: Repository) : ViewModel() {
    fun getTheme(): LiveData<Boolean>{
        return repository.getTheme().asLiveData()
    }

    fun saveTheme(isDarkModeActive: Boolean){
        viewModelScope.launch {
            repository.saveTheme(isDarkModeActive)
        }
    }
}