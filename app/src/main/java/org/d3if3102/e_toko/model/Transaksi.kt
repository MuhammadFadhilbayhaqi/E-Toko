package org.d3if3102.e_toko.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama_barang: String,
    val harga: Double,
    val pembayaran: String,
    val jumlah: Int
)
