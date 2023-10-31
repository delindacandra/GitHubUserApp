package com.dicoding.githubuserapp.di

import android.content.Context
import com.dicoding.githubuserapp.data.local.database.DbFavorite
import com.dicoding.githubuserapp.data.local.database.Repository
import com.dicoding.githubuserapp.ui.settings.SettingPreferences
import com.dicoding.githubuserapp.ui.settings.dataStore
import com.dicoding.githubuserapp.utils.AppExecutors

object Injection {
    fun  provideRepository(context: Context): Repository{
        val database = DbFavorite.getInstance(context)
        val dao = database.daoFav()
        val appExecutors = AppExecutors()
        val dataStore = context.dataStore
        val settingPreferences= SettingPreferences.getInstance(dataStore)
        return Repository.getInstance(dao, appExecutors, settingPreferences)
    }
}