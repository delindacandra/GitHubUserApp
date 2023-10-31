package com.dicoding.githubuserapp.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    var login: String= "",
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null
) : Parcelable