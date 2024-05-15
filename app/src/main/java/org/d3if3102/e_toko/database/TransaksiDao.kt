package org.d3if3102.e_toko.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3102.e_toko.model.Transaksi


@Dao
interface TransaksiDao {
    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi ORDER BY harga DESC")
    fun getTransaksi(): Flow<List<Transaksi>>

    @Query("SELECT * From transaksi Where id = :id")
    suspend fun getTransaksiById(id: Long): Transaksi?

    @Query("DELETE FROM transaksi WHERE id= :id")
    suspend fun deleteById(id: Long)
}