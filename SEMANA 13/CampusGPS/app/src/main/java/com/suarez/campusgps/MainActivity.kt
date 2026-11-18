package com.suarez.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.suarez.campusgps.ui.theme.CampusGpsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusGpsTheme {
                Surface(color = MaterialTheme.colorScheme.background) { Text("CampusGPS") }
            }
        }
    }
}
