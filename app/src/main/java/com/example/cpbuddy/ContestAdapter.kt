package com.example.cpbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
class ContestAdapter(private val contests: List<Contest>) :
    RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {

    // 1. This "ViewHolder" finds the views in your item_contest.xml
    class ContestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvContestName)
        val siteText: TextView = view.findViewById(R.id.tvContestSite)
        val startTimeText: TextView = view.findViewById<TextView>(R.id.tvStartTime)
        val durationText: TextView = view.findViewById<TextView>(R.id.tvDuration)
        val siteLogo: ImageView = view.findViewById(R.id.ivSiteLogo)
    }

    // 2. This creates the row layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contest, parent, false)
        return ContestViewHolder(view)
    }

    // 3. This puts the actual data (name, site) into the views
    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val contest = contests[position]
        holder.nameText.text = contest.title
        holder.siteText.text = contest.site

        holder.startTimeText.text = formatStartTime(contest.startTime)
        holder.durationText.text = formatDuration(contest.duration, contest.site)

        // Set site logo based on site name
        val logoResId = when (contest.site.lowercase()) {
            "codeforces" -> R.drawable.codeforces
            "leetcode" -> R.drawable.leetcode
            "codechef" -> R.drawable.codechef
            else -> android.R.drawable.ic_menu_help // Default icon if site not recognized
        }
        holder.siteLogo.setImageResource(logoResId)
    }

    private fun formatStartTime(timeInMillis: Long): String {
        // 1. Create a date object from the milliseconds
        val date = Date(timeInMillis)

        // 2. Define the pattern (E = Day, MMM = Month, dd = Date, hh:mm = Time, a = AM/PM)
        val sdf = SimpleDateFormat("E, MMM dd, hh:mm a", Locale.getDefault())

        // 3. Format the date into a String
        return sdf.format(date)
    }

    private fun formatDuration(durationMillis: Long,url: String): String {
        if(url.equals("leetcode", ignoreCase = true)){
            return "1h 30m"
        }
        val totalSeconds = durationMillis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60

        return "${hours}h ${minutes}m"
    }

    override fun getItemCount() = contests.size
}