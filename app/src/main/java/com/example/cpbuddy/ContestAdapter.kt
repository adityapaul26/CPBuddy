package com.example.cpbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContestAdapter(private val contests: List<Contest>) :
    RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {

    // 1. This "ViewHolder" finds the views in your item_contest.xml
    class ContestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvContestName)
        val siteText: TextView = view.findViewById(R.id.tvContestSite)
    }

    // 2. This creates the row layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contest, parent, false)
        return ContestViewHolder(view)
    }

    // 3. This puts the actual data (name, site) into the views
    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        // Inside onBindViewHolder, find these lines:
        val contest = contests[position]
        holder.nameText.text = contest.title // Changed from .name to .title
        holder.siteText.text = "Platform: ${contest.site}"
    }

    override fun getItemCount() = contests.size
}