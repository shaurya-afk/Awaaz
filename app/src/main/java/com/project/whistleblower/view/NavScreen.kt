package com.project.whistleblower.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.whistleblower.Routes

@Composable
fun NavScreen(){
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.splash) {
                SplashScreen(navController)
            }
            composable(Routes.home) {
                HomeScreen(navController)
            }
            composable(Routes.complaint) {
                ComplaintScreen()
            }
            composable(Routes.about) {
                AboutScreen(navController)
            }
        }
    }
}