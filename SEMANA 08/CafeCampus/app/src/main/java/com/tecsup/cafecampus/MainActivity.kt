package com.tecsup.cafecampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.tecsup.cafecampus.ui.navigation.NavCafe
import com.tecsup.cafecampus.ui.theme.CafeTheme
import com.tecsup.cafecampus.viewmodel.CafeViewModelFactory
import com.tecsup.cafecampus.viewmodel.MenuViewModel

class MainActivity : ComponentActivity() {

    private val menuViewModel: MenuViewModel by viewModels {
        CafeViewModelFactory((application as CafeApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        enableEdgeToEdge()
        setContent {
            CafeTheme {
                NavCafe(menuViewModel = menuViewModel)
            }
        }
    }
}
