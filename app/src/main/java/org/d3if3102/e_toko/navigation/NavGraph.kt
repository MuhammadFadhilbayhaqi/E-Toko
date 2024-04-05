package org.d3if3102.e_toko.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3102.e_toko.ui.screen.AboutMeScreen
import org.d3if3102.e_toko.ui.screen.AboutProjectScreen
import org.d3if3102.e_toko.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.AboutProject.route) {
            AboutProjectScreen(navController)
        }
        composable(route= Screen.AboutMe.route) {
            AboutMeScreen(navController)
        }
    }
}
