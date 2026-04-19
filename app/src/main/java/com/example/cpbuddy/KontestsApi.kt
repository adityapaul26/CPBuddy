package com.example.cpbuddy

import retrofit2.Call
import retrofit2.http.GET

interface CompeteApi {
    // The endpoint is "contests/upcoming/"
    @GET("contests/upcoming/")
    fun getUpcomingContests(): Call<List<Contest>>
}