package com.dicoding.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.local.database.Repository
import com.dicoding.githubuserapp.data.remote.response.DetailResponse
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _userData = MutableLiveData<DetailResponse>()
    val userData: MutableLiveData<DetailResponse> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "DetailViewModel"

    }


    fun saveFav(login: String, avatarUrl:String){
        repository.setFavoriteUser(login, avatarUrl)
    }
    fun getFav(login: String) = repository.getFav(login)
    fun deleteFav(login: String, avatarUrl: String){
        repository.deleteFav(login, avatarUrl)
    }

    fun fetchUserData(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserName(login)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userData = response.body()
                    _userData.value = userData!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}

