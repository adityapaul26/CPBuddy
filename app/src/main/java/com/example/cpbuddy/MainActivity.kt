package com.example.cpbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Show loading
        progressBar.visibility = View.VISIBLE

        // 1. New Base URL
        val retrofit = Retrofit.Builder()
            .baseUrl("https://competeapi.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CompeteApi::class.java)

        // 2. Make the call
        api.getUpcomingContests().enqueue(object : Callback<List<Contest>> {
            override fun onResponse(call: Call<List<Contest>>, response: Response<List<Contest>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val contestList = response.body() ?: emptyList()
                    // 3. Set the adapter
                    recyclerView.adapter = ContestAdapter(contestList)
                }
            }

            override fun onFailure(call: Call<List<Contest>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_contests // Highlight the current tab

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_contests -> true // Already here
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0) // Removes the "jumpy" animation
                    finish() // Optional: close current activity so the back button doesn't loop
                    true
                }
                else -> false
            }
        }
    }
}