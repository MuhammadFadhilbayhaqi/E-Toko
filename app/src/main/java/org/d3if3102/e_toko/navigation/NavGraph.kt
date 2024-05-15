package org.d3if3102.e_toko.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3102.e_toko.ui.screen.AboutMeScreen
import org.d3if3102.e_toko.ui.screen.AboutProjectScreen
import org.d3if3102.e_toko.ui.screen.KEY_ID_TRANSAKSI
import org.d3if3102.e_toko.ui.screen.MainScreen
import org.d3if3102.e_toko.ui.screen.ScreenContent
import org.d3if3102.e_toko.ui.screen.TransaksiScreen

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
        composable(route = Screen.FormBaru.route) {
            TransaksiScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_TRANSAKSI) { type = NavType.LongType}
            )
        ) {navBackStackEntry->
            val id =navBackStackEntry.arguments?.getLong(KEY_ID_TRANSAKSI)
            ScreenContent(navController, id)
        }
    }
}
