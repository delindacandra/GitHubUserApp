package com.dicoding.githubuserapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.local.database.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getFavList() = repository.getFavList()
}