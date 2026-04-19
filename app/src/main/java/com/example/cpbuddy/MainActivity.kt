package com.example.cpbuddy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rvContests)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. New Base URL
        val retrofit = Retrofit.Builder()
            .baseUrl("https://competeapi.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CompeteApi::class.java)

// 2. Make the call
        api.getUpcomingContests().enqueue(object : Callback<List<Contest>> {
            override fun onResponse(call: Call<List<Contest>>, response: Response<List<Contest>>) {
                if (response.isSuccessful) {
                    val contestList = response.body() ?: emptyList()
                    // 3. Set the adapter
                    recyclerView.adapter = ContestAdapter(contestList)
                }
            }

            override fun onFailure(call: Call<List<Contest>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}