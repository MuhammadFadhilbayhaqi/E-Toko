package org.d3if3102.e_toko.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3102.e_toko.database.TransaksiDao
import org.d3if3102.e_toko.model.Transaksi

class MainViewModel(dao: TransaksiDao) : ViewModel () {
    val data: StateFlow<List<Transaksi>> = dao.getTransaksi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
