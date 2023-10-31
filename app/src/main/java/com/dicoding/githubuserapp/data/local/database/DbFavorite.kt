package com.dicoding.githubuserapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class DbFavorite : RoomDatabase(){
    abstract fun daoFav() : DaoFav

    companion object{
        @Volatile
        private var instance: DbFavorite? = null
        fun getInstance(context: Context): DbFavorite =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DbFavorite::class.java, "favorite_user.db"
                ).build()
            }
    }
}