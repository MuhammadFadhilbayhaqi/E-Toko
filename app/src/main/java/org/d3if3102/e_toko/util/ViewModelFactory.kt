package org.d3if3102.e_toko.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3102.e_toko.database.TransaksiDao
import org.d3if3102.e_toko.ui.screen.MainViewModel
import org.d3if3102.e_toko.ui.screen.TransaksiViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory (
    private val dao: TransaksiDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(TransaksiViewModel::class.java)) {
            return TransaksiViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}