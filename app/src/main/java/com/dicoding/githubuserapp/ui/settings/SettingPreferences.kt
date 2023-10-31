package com.dicoding.githubuserapp.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingPreferences private constructor(private  val dataStore: DataStore<Preferences>){

    private val _themeKey = booleanPreferencesKey("theme_setting")

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[_themeKey] ?: false
        }
    }

    suspend fun saveTheme(isDarkModeActive: Boolean){
        dataStore.edit { preferences ->
            preferences[_themeKey] = isDarkModeActive
        }
    }

    companion object{
        @Volatile
        private var instance: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return instance ?: synchronized(this){
                val instances = SettingPreferences(dataStore)
                instance = instances
                instances
            }
        }
    }
}