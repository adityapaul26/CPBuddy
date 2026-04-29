package com.example.cpbuddy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cpbuddy.Contest
import com.example.cpbuddy.R
import com.example.cpbuddy.ui.components.GlassBox
import com.example.cpbuddy.ui.components.ImmersiveBackground
import com.example.cpbuddy.ui.theme.CPBuddyTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ContestsScreen(contests: List<Contest>) {
    val nextContest = contests
        .filter { it.startTime > System.currentTimeMillis() }
        .minByOrNull { it.startTime }

    ImmersiveBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "CPBuddy",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            
            val sdfDate = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            Text(
                text = sdfDate.format(Date()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (nextContest != null) {
                Text(
                    text = "Next Contest",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                GlassBox(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = getSiteLogo(nextContest.site)),
                            contentDescription = nextContest.site,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = nextContest.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = nextContest.site,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    val sdfFull = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
                    Text(
                        text = "Starts: ${sdfFull.format(Date(nextContest.startTime))}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CountdownTimer(nextContest.startTime)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }

            Text(
                text = "Upcoming Contests",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(contests) { contest ->
                    ContestItem(contest)
                }
            }
        }
    }
}

@Composable
fun ContestItem(contest: Contest) {
    GlassBox(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 16.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = getSiteLogo(contest.site)),
                contentDescription = contest.site,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contest.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
                Text(
                    text = contest.site,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            val sdfDateTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
            Text(
                text = sdfDateTime.format(Date(contest.startTime)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun getSiteLogo(site: String): Int {
    return when (site.lowercase()) {
        "codeforces" -> R.drawable.codeforces
        "codechef" -> R.drawable.codechef
        "leetcode" -> R.drawable.leetcode
        else -> R.drawable.ic_launcher_foreground
    }
}

@Composable
fun CountdownTimer(startTime: Long) {
    var timeLeft by remember { mutableStateOf(startTime - System.currentTimeMillis()) }

    LaunchedEffect(key1 = startTime) {
        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000)
            timeLeft = startTime - System.currentTimeMillis()
        }
    }

    val hours = (timeLeft / (1000 * 60 * 60))
    val minutes = (timeLeft / (1000 * 60)) % 60
    val seconds = (timeLeft / 1000) % 60

    val timeString = if (timeLeft > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        "STARTED!"
    }

    Text(
        text = timeString,
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Black,
        letterSpacing = 2.sp
    )
}

@Preview(showBackground = true)
@Composable
fun ContestsScreenPreview() {
    CPBuddyTheme {
        ContestsScreen(
            contests = listOf(
                Contest("Codeforces", "Round 900 (Div. 2)", System.currentTimeMillis() + 3600000, 7200, "https://codeforces.com"),
                Contest("LeetCode", "Weekly Contest 350", System.currentTimeMillis() + 86400000, 5400, "https://leetcode.com")
            )
        )
    }
}
