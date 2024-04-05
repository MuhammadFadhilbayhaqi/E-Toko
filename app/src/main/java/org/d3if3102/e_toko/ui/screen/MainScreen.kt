package org.d3if3102.e_toko.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3102.e_toko.R
import org.d3if3102.e_toko.navigation.Screen
import org.d3if3102.e_toko.ui.theme.ETokoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.AboutProject.route)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.about_aplication),
                            tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ){ padding ->
        Box(modifier = Modifier.padding(padding)) {
            ScreenContent(Modifier.padding((padding)))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ScreenContent(modifier: Modifier) {
    var namaBarang by rememberSaveable { mutableStateOf("")}
    var namaBarangError by rememberSaveable { mutableStateOf(false)}
    var harga by rememberSaveable { mutableStateOf("")}
    var hargaError by rememberSaveable { mutableStateOf(false)}
    var jumlah by rememberSaveable { mutableStateOf("")}
    var jumlahError by rememberSaveable { mutableStateOf(false)}
    var total by rememberSaveable { mutableFloatStateOf(0f)}
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(id = R.string.cashier_menu),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = namaBarang,
            onValueChange = {namaBarang = it},
            label = { Text(text = stringResource(id = R.string.product_name))},
            trailingIcon = { IconPicker(namaBarangError)},
            supportingText = { ErrorHint(namaBarangError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            onValueChange = {harga = it},
            label = { Text(text = stringResource(id = R.string.price))},
            trailingIcon = { IconPicker(hargaError)},
            supportingText = { ErrorHint(hargaError)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jumlah,
            onValueChange =  {jumlah = it},
            label = { Text(text = stringResource(id = R.string.quantity))},
            trailingIcon = { IconPicker(jumlahError)},
            supportingText = { ErrorHint(jumlahError)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                namaBarangError = (namaBarang == "" || namaBarang == "0")
                hargaError = (harga == "" || harga == "0")
                jumlahError = (jumlah == "" || jumlah == "0")
                if  (namaBarangError || hargaError || jumlahError) return@Button
                      total = hitungTotal(harga.toFloat(), jumlah.toFloat())
            },
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.count) )
        }

        if ( total != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp
            )
            Text(
                text = stringResource(id = R.string.total_price, total),
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = {
                          shareData(
                              context = context,
                              message = context.getString(R.string.share_templete,
                                  namaBarang, harga, jumlah, total.toString().uppercase())
                          )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.share))
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

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreePreview() {
    ETokoTheme {
        MainScreen(rememberNavController())
    }
}