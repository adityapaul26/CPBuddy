package com.example.cpbuddy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeforcesApi {
    @GET("user.info")
    fun getUserInfo(@Query("handles") handle: String): Call<List<User>>
}