package com.dicoding.githubuserapp.data.remote.retrofit

import com.dicoding.githubuserapp.data.remote.response.DetailResponse
import com.dicoding.githubuserapp.data.remote.response.GitHubResponse
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") id: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getUserName(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}
