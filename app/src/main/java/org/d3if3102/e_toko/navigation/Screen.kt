package org.d3if3102.e_toko.navigation

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object AboutProject: Screen("aboutProjectScreen")
    data object AboutMe: Screen("aboutMeScreen")
}