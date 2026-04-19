package com.example.cpbuddy

// This is your data model. It tells the app:
// "Every Contest has a name, a URL, a start time, a site, and a duration."
data class Contest(
    val site: String,
    val title: String,
    val startTime: Long, // It's a big number, so we use Long
    val duration: Long,
    val url: String
)