package com.example.cpbuddy

data class User(
    val contribution: Int,
    val lastOnlineTimeSeconds: Long,
    val rating: Int,
    val friendOfCount: Int,
    val titlePhoto: String,
    val rank: String,
    val handle: String,
    val maxRating: Int,
    val avatar: String,
    val registrationTimeSeconds: String,
    val maxRank: String
)

//"contribution": 0,
//"lastOnlineTimeSeconds": 1660836836,
//"rating": 349,
//"friendOfCount": 1,
//"titlePhoto": "https://cdn-userpic.codeforces.com/no-title.jpg",
//"rank": "newbie",
//"handle": "Bharanispace",
//"maxRating": 349,
//"avatar": "https://cdn-userpic.codeforces.com/no-avatar.jpg",
//"registrationTimeSeconds": 1655563973,
//"maxRank": "newbie"