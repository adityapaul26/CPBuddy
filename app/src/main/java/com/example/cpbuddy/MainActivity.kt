package com.example.cpbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cpbuddy.ui.screens.ContestsScreen
import com.example.cpbuddy.ui.screens.ProfileScreen
import com.example.cpbuddy.ui.theme.CPBuddyTheme

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Contests : Screen("contests", "Contests", Icons.AutoMirrored.Filled.List)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CPBuddyTheme {
                CPBuddyApp()
            }
        }
    }
}

@Composable
fun CPBuddyApp(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val contestsState by viewModel.contestsState
    val userProfileState by viewModel.userProfileState

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val items = listOf(Screen.Contests, Screen.Profile)

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    )
 { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Contests.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Contests.route) {
                when (val state = contestsState) {
                    is UiState.Loading -> LoadingScreen()
                    is UiState.Success -> ContestsScreen(state.data)
                    is UiState.Error -> ErrorScreen(state.message) { viewModel.fetchContests() }
                }
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    userState = userProfileState,
                    onSearch = { handle -> viewModel.searchUser(handle) }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onRetry) {
            Text("Error: $message. Retry?")
        }
    }
}

