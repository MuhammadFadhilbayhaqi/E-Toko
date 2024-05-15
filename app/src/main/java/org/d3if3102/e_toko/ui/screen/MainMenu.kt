package org.d3if3102.e_toko.ui.screen

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3102.e_toko.ui.theme.ETokoTheme

@Composable
fun MainMenu () {
    
}

@Preview (showBackground = true)
@Preview (uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainMenuPreview() {
    ETokoTheme {
        MainMenu()
    }   
}


