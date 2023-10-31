package com.dicoding.githubuserapp.data.local.database


import com.dicoding.githubuserapp.ui.settings.SettingPreferences
import com.dicoding.githubuserapp.utils.AppExecutors
import kotlinx.coroutines.flow.Flow


class Repository private constructor(
    private  val daoFav: DaoFav,
    private val appExecutors: AppExecutors,
    private val settingPreferences: SettingPreferences
) {


    fun setFavoriteUser(login:String, avatarUrl:String){
        appExecutors.diskIO.execute {
            val favoriteEntity = FavoriteEntity(login, avatarUrl)
            daoFav.insertFav(favoriteEntity)
        }
    }
    fun getFav(login: String) = daoFav.getFav(login)

    fun deleteFav(login: String, avatarUrl: String){
        appExecutors.diskIO.execute {
            val favoriteEntity = FavoriteEntity(login, avatarUrl)
            daoFav.deleteFav(favoriteEntity)
        }
    }

    fun getFavList() = daoFav.getFavList()

    fun getTheme(): Flow<Boolean> = settingPreferences.getTheme()

    suspend fun saveTheme(isDarkModeActive: Boolean)=
        settingPreferences.saveTheme(isDarkModeActive)


    companion object{
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            daoFav: DaoFav,
            appExecutors: AppExecutors,
            settingPreferences: SettingPreferences
        ): Repository = instance ?: synchronized(this){
            instance ?: Repository(daoFav, appExecutors, settingPreferences)
        }.also { instance = it }
    }
}