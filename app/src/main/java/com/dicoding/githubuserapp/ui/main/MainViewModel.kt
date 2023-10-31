package com.dicoding.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubuserapp.data.local.database.Repository
import com.dicoding.githubuserapp.data.remote.response.GitHubResponse
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _items = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        findSearchUser()
    }

    private fun findSearchUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(USER_ID)
        client.enqueue(object: Callback<GitHubResponse> {
            override fun onResponse(call: Call<GitHubResponse>, response: Response<GitHubResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _items.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUsers(query:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(call: Call<GitHubResponse>, response: Response<GitHubResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val totalCount = response.body()?.totalCount!!
                    _isEmpty.value = totalCount <= 0
                    _items.value = response.body()?.items!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")

            }
        })
    }

    fun getTheme(): LiveData<Boolean> = repository.getTheme().asLiveData()

    companion object{
        private const val TAG = "MainViewModel"
        private const val  USER_ID = "arif"
    }
}