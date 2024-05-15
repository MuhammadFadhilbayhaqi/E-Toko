package org.d3if3102.e_toko.ui.screen

import androidx.lifecycle.ViewModel
import org.d3if3102.e_toko.database.TransaksiDao
import org.d3if3102.e_toko.model.Transaksi
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransaksiViewModel(private val transaksiDao: TransaksiDao) : ViewModel() {

    fun insertTransaksi(namaBarang: String, harga: Double, pembayaran: String, jumlah: Int) {
        val transaksi = Transaksi(
            nama_barang = namaBarang,
            harga = harga,
            pembayaran = pembayaran,
            jumlah = jumlah
        )
        viewModelScope.launch(Dispatchers.IO) {
            transaksiDao.insert(transaksi)
        }
    }

    suspend fun getTransaksiById(id: Long): Transaksi? {
        return transaksiDao.getTransaksiById(id)
    }

    fun updateTransaksi(id: Long, namaBarang: String, harga: Double, pembayaran: String, jumlah: Int) {
        val transaksi = Transaksi(
            id = id,
            nama_barang = namaBarang,
            harga = harga,
            pembayaran = pembayaran,
            jumlah = jumlah
        )
        viewModelScope.launch(Dispatchers.IO) {
            transaksiDao.update(transaksi)
        }
    }

    fun deleteTransaksi(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            transaksiDao.deleteById(id)
        }
    }
}
