package com.example.cpbuddy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cpbuddy.User
import com.example.cpbuddy.UiState
import com.example.cpbuddy.ui.components.GlassBox
import com.example.cpbuddy.ui.components.ImmersiveBackground
import com.example.cpbuddy.ui.theme.CPBuddyTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userState: UiState<User>?,
    onSearch: (String) -> Unit
) {
    var handleInput by remember { mutableStateOf("") }

    ImmersiveBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            TextField(
                value = handleInput,
                onValueChange = { handleInput = it },
                placeholder = { Text("Enter Codeforces Handle", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    IconButton(onClick = { onSearch(handleInput) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            when (userState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                is UiState.Success -> {
                    UserProfileCard(userState.data)
                }
                is UiState.Error -> {
                    Text(
                        text = userState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                null -> {
                    Text(
                        text = "Search for a user to see their stats",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun UserProfileCard(user: User) {
    val rankColor = getRankColor(user.rating)
    
    GlassBox(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = user.avatar,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = user.handle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = user.rank.uppercase(),
                style = MaterialTheme.typography.bodyLarge,
                color = rankColor,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Rating", user.rating.toString(), rankColor)
                StatItem("Max", user.maxRating.toString(), MaterialTheme.colorScheme.onSurface)
                StatItem("Friends", user.friendOfCount.toString(), MaterialTheme.colorScheme.onSurface)
            }

            Spacer(modifier = Modifier.height(16.dp))

            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            Text(
                text = "Joined: ${sdf.format(Date(user.registrationTimeSeconds * 1000))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.titleLarge, color = color, fontWeight = FontWeight.Bold)
    }
}

private fun getRankColor(rating: Int): Color {
    return when {
        rating >= 3000 -> Color(0xFFFF0000) // Legendary Grandmaster
        rating >= 2400 -> Color(0xFFFF0000) // Grandmaster
        rating >= 2100 -> Color(0xFFFFBB55) // Master
        rating >= 1900 -> Color(0xFFAA00AA) // Candidate Master
        rating >= 1600 -> Color(0xFF0000FF) // Expert
        rating >= 1400 -> Color(0xFF03A89E) // Specialist
        rating >= 1200 -> Color(0xFF008000) // Pupil
        else -> Color(0xFF808080)           // Newbie
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CPBuddyTheme {
        ProfileScreen(
            userState = UiState.Success(
                User(
                    contribution = 10,
                    lastOnlineTimeSeconds = 1660836836,
                    rating = 1800,
                    friendOfCount = 42,
                    titlePhoto = "",
                    rank = "expert",
                    handle = "CP_Master",
                    maxRating = 1950,
                    avatar = "https://cdn-userpic.codeforces.com/no-avatar.jpg",
                    registrationTimeSeconds = 1655563973,
                    maxRank = "candidate master"
                )
            ),
            onSearch = {}
        )
    }
}
