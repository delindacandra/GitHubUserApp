package com.dicoding.githubuserapp.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoFav {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite WHERE login = :login")
    fun getFav(login: String): LiveData<FavoriteEntity>

    @Delete
    fun deleteFav(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getFavList(): LiveData<List<FavoriteEntity>>
}