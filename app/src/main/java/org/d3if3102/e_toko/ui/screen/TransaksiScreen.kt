package org.d3if3102.e_toko.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3102.e_toko.R
import org.d3if3102.e_toko.database.TransaksiDb
import org.d3if3102.e_toko.ui.theme.ETokoTheme
import org.d3if3102.e_toko.util.ViewModelFactory

const val KEY_ID_TRANSAKSI = "idtransaksi"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaksiScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ScreenContent(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(navController: NavHostController, id: Long? = null) {
    var namaBarang by rememberSaveable { mutableStateOf("") }
    var namaBarangError by rememberSaveable { mutableStateOf(false) }
    var harga by rememberSaveable { mutableStateOf("") }
    var hargaError by rememberSaveable { mutableStateOf(false) }
    var jumlah by rememberSaveable { mutableStateOf("") }
    var jumlahError by rememberSaveable { mutableStateOf(false) }
    var total by rememberSaveable { mutableFloatStateOf(0f) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val items = listOf(
        stringResource(id = R.string.payment_method_cash),
        stringResource(id = R.string.payment_method_qris)
    )
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var paymentMethod by rememberSaveable { mutableStateOf(items.first()) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = TransaksiDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: TransaksiViewModel = viewModel(factory = factory)
    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTransaksiById(id) ?: return@LaunchedEffect
        namaBarang = data.nama_barang
        harga = data.harga.toString()
        paymentMethod = data.pembayaran
        jumlah = data.jumlah.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.cashier_menu),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = namaBarang,
            onValueChange = { namaBarang = it },
            label = { Text(text = stringResource(id = R.string.product_name)) },
            trailingIcon = { IconPicker(namaBarangError) },
            supportingText = { ErrorHint(namaBarangError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            onValueChange = { harga = it },
            label = { Text(text = stringResource(id = R.string.price)) },
            trailingIcon = { IconPicker(hargaError) },
            supportingText = { ErrorHint(hargaError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                readOnly = false,
                value = paymentMethod,
                onValueChange = { },
                label = { Text(stringResource(id = R.string.select_payment_method)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = true
                }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            selectedIndex = index
                            paymentMethod = items[index]
                            expanded = true
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = jumlah,
            onValueChange = { jumlah = it },
            label = { Text(text = stringResource(id = R.string.quantity)) },
            trailingIcon = { IconPicker(jumlahError) },
            supportingText = { ErrorHint(jumlahError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                namaBarangError = namaBarang.isBlank()
                hargaError = harga.isBlank()
                jumlahError = jumlah.isBlank()

                if (namaBarangError || hargaError || jumlahError) {
                    return@Button
                }

                total = hitungTotal(harga.toFloat(), jumlah.toFloat())
            },
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.count))
        }
        if (id != null) {
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.hapus),
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        DisplayAlertDialog(
            openDialog = showDialog,
            onDismissRequest = { showDialog = false }
        ) {
            showDialog = false
            if (id != null) {
                viewModel.deleteTransaksi(id)
                navController.popBackStack()
            }
        }

        if (total != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp
            )
            OutlinedTextField(
                value = total.toString(),
                onValueChange = { },
                modifier = Modifier.padding(vertical = 8.dp),
                textStyle = MaterialTheme.typography.titleLarge,
                label = { Text(text = stringResource(id = R.string.total_price)) },
                readOnly = true
            )
            Button(
                onClick = {
                    if (id == null) {
                        viewModel.insertTransaksi(namaBarang, harga.toDouble(), paymentMethod, jumlah.toInt())
                    } else {
                        viewModel.updateTransaksi(id, namaBarang, harga.toDouble(), paymentMethod, jumlah.toInt())
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.simpan),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun IconPicker(isError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

private fun hitungTotal(harga: Float, jumlah: Float): Float {
    return harga * jumlah
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TransaksiScreenPreview() {
    ETokoTheme {
        TransaksiScreen(rememberNavController())
    }
}
