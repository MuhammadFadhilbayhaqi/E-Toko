package org.d3if3102.e_toko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import org.d3if3102.e_toko.model.DeveloperImage
import org.d3if3102.e_toko.navigation.SetupNavGraph
import org.d3if3102.e_toko.ui.theme.ETokoTheme

class MainActivity : ComponentActivity() {
    private val data = getData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ETokoTheme {

                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph()
                }
            }
        }
    }
    private fun getData(): List<DeveloperImage> {
        return listOf(
            DeveloperImage("fadhil", R.mipmap.fadhil)
        )
    }
}