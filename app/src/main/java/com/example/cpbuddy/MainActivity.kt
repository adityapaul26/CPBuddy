package com.example.cpbuddy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set today's date
        val tvTodayDate = findViewById<TextView>(R.id.tvTodayDate)
        val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        tvTodayDate.text = sdf.format(Date())

        val recyclerView = findViewById<RecyclerView>(R.id.rvContests)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val cvNextContest = findViewById<MaterialCardView>(R.id.cvNextContest)
        val tvCountdown = findViewById<TextView>(R.id.tvCountdown)
        
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

                    // Find next contest for countdown
                    val nextContest = contestList
                        .filter { it.startTime > System.currentTimeMillis() }
                        .minByOrNull { it.startTime }

                    if (nextContest != null) {
                        cvNextContest.visibility = View.VISIBLE
                        startCountdown(nextContest.startTime, tvCountdown)
                    }
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
                    finish() 
                    true
                }
                else -> false
            }
        }
    }

    private fun startCountdown(startTime: Long, tvCountdown: TextView) {
        countDownTimer?.cancel()
        val remainingTime = startTime - System.currentTimeMillis()

        if (remainingTime > 0) {
            countDownTimer = object : CountDownTimer(remainingTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = (millisUntilFinished / 1000) % 60
                    val minutes = (millisUntilFinished / (1000 * 60)) % 60
                    val hours = (millisUntilFinished / (1000 * 60 * 60))
                    
                    tvCountdown.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
                }

                override fun onFinish() {
                    tvCountdown.text = "STARTED!"
                }
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}