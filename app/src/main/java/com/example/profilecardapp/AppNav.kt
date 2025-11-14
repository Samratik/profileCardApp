package com.example.profilecardapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val vm: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME)        { HomeScreen(nav) }
        composable(Routes.PROFILE)     { ProfileScreen(nav = nav, vm = vm) }
        composable(Routes.EDITPROFILE) { EditProfileScreen(nav = nav, vm = vm) }
        composable(Routes.USERS)       { UserListScreen() }
    }

}