package com.example.cpbuddy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodeforcesApi {
    // The {handle} in the curly braces is a placeholder
    @GET("user/codeforces/{handle}/")
    fun getUserInfo(@Path("handle") handle: String): Call<List<User>>
}