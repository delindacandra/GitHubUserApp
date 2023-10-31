package com.dicoding.githubuserapp.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import com.dicoding.githubuserapp.ui.detail.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentViewModel : ViewModel() {
    private val _followers =  MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following =  MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()

    fun getFollowers(follow: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(follow)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }
        })
    }

    fun getFollowing(follow: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(follow)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }
        })
    }
}