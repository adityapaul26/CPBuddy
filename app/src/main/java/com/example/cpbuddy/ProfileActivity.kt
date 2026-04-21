package com.example.cpbuddy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_contests // Highlight the current tab

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> true // Already here
                R.id.nav_contests -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }

        val searchButton: Button = findViewById<Button>(R.id.btnSearch)
        val handle = findViewById<EditText>(R.id.etHandle)
        searchButton.setOnClickListener {
            val handle = handle.text.toString().trim()
            if (handle.isNotEmpty()) {
                // Check: Is this function actually getting called?
                fetchUserData(handle)
            } else {
                Toast.makeText(this, "Enter a handle!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUserData(handle:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://competeapi.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api= retrofit.create(CodeforcesApi::class.java)

        api.getUserInfo(handle).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {

                    val userProfile = response.body()!![0]// we only take the first element acc to the api
                    // mind you userProfile has a datatype of 'User'

                    bindUserToUI(userProfile)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindUserToUI(userProfile: User){
        val tvRating = findViewById<TextView>(R.id.tvCurrentRating)
        val tvRank = findViewById<TextView>(R.id.tvUserRank)
        val tvMaxRating = findViewById<TextView>(R.id.tvMaxRating)

        val rankColor = getRankColor(userProfile.rating)

        tvRating.text = userProfile.rating?.toString() ?: "0"
        tvRating.setTextColor(rankColor)

        tvRank.text = userProfile.rank

        tvMaxRating.text = userProfile.maxRating.toString()

    }

    private fun formatTimeStamps(seconds: Long): String{
        val date = java.util.Date(seconds * 1000)
        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
        return sdf.format(date)
    }

    private fun getRankColor(rating: Int?): Int {
        if (rating == null) return Color.parseColor("#808080") // Gray for unrated

        return when {
            rating >= 3000 -> Color.parseColor("#FF0000") // Legendary Grandmaster (Red)
            rating >= 2600 -> Color.parseColor("#FF0000") // International Grandmaster (Red)
            rating >= 2400 -> Color.parseColor("#FF0000") // Grandmaster (Red)
            rating >= 2300 -> Color.parseColor("#FFBB55") // International Master (Orange)
            rating >= 2100 -> Color.parseColor("#FFBB55") // Master (Orange)
            rating >= 1900 -> Color.parseColor("#AA00AA") // Candidate Master (Purple)
            rating >= 1600 -> Color.parseColor("#0000FF") // Expert (Blue)
            rating >= 1400 -> Color.parseColor("#03A89E") // Specialist (Cyan)
            rating >= 1200 -> Color.parseColor("#008000") // Pupil (Green)
            else -> Color.parseColor("#808080")           // Newbie (Gray)
        }
    }
}