package com.suarez.mibodega

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suarez.mibodega.ui.cliente.ClienteApp
import com.suarez.mibodega.ui.theme.BodegaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BodegaTheme {
                ClienteApp()
            }
        }
    }
}