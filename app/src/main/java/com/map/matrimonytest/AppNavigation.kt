package com.map.matrimonytest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.HomeScreen
import com.map.matrimonytest.screens.ProfileDetailScreen
import com.map.matrimonytest.screens.ProfileGestureScreen
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(viewModel: ProfileViewModel) {
    // Create the NavHost
    val navController = rememberNavController()
    NavHost(navController, startDestination = "screen1") {
        val gson = Gson()
        composable("screen1") { HomeScreen(navController, gson, viewModel) }
        composable("screen2") { ProfileGestureScreen(navController, viewModel)}
        composable("screen3/{profileJson}") { backStackEntry ->
            val profileJson = backStackEntry.arguments?.getString("profileJson")
            val profile = gson.fromJson(profileJson, ProfileEntity::class.java)
            ProfileDetailScreen(navController, profile)
        }
    }
}
