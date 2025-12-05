package com.example.profilecardapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val profileVm: ProfileViewModel = viewModel()

    val items = listOf(
        Routes.HOME,
        Routes.USERS,
        Routes.FEED
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by nav.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                items.forEach { route ->
                    val (icon, label) = when (route) {
                        Routes.HOME  -> Icons.Default.Person to "Profile"
                        Routes.USERS -> Icons.AutoMirrored.Filled.List to "Users"
                        Routes.FEED  -> Icons.Default.Home to "Feed"
                        else -> Icons.Default.Home to route
                    }

                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            if (currentRoute != route) {
                                nav.navigate(route) {
                                    popUpTo(Routes.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME)  { ProfileScreen(nav = nav, vm = profileVm) }
            composable(Routes.EDITPROFILE) { EditProfileScreen(nav = nav, vm = profileVm) }
            composable(Routes.USERS) { UserListScreen() }
            composable(Routes.FEED)  { FeedScreen() }
        }
    }
}
