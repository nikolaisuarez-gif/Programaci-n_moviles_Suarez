package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.data.local.entity.Producto

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAgregarClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            ProductoIcono(
                icono = producto.icono,
                size = 56.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "S/ %.2f".format(producto.precio),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                if (onAgregarClick != null) {
                    IconButton(
                        onClick = onAgregarClick,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
