package com.dicoding.githubuserapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserapp.data.local.database.Repository
import com.dicoding.githubuserapp.di.Injection
import com.dicoding.githubuserapp.ui.detail.DetailViewModel
import com.dicoding.githubuserapp.ui.favorite.FavoriteViewModel
import com.dicoding.githubuserapp.ui.main.MainViewModel
import com.dicoding.githubuserapp.ui.settings.ThemeViewModel

class ViewModelFactory private constructor(
    private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}