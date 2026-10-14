package com.tecsup.cafecampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.ProductoCard
import com.tecsup.cafecampus.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    vm: MenuViewModel,
    onProductoClick: (Int) -> Unit
) {
    val productos by vm.productos.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Café Campus") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Menú del día",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(productos, key = { it.id }) { p ->
                    ProductoCard(
                        producto = p,
                        onClick = { onProductoClick(p.id) }
                    )
                }
            }
        }
    }
}
