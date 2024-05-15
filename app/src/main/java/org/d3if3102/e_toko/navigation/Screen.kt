package org.d3if3102.e_toko.navigation

import org.d3if3102.e_toko.ui.screen.KEY_ID_TRANSAKSI

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object AboutProject: Screen("aboutProjectScreen")
    data object AboutMe: Screen("aboutMeScreen")
    data object FormBaru: Screen("transaksiscreen")
    data object FormUbah: Screen("transaksiscreen/{$KEY_ID_TRANSAKSI}") {
        fun withId(id: Long)= "transaksiscreen/$id"
    }
}