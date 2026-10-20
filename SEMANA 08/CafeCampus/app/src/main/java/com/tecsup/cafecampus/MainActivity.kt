package com.tecsup.cafecampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tecsup.cafecampus.ui.navigation.NavCafe
import com.tecsup.cafecampus.ui.theme.CafeTheme
import com.tecsup.cafecampus.viewmodel.CafeViewModelFactory
import com.tecsup.cafecampus.viewmodel.MenuAdminViewModel
import com.tecsup.cafecampus.viewmodel.MenuViewModel
import com.tecsup.cafecampus.viewmodel.PanelAdminViewModel
import com.tecsup.cafecampus.viewmodel.PedidosAdminViewModel
import com.tecsup.cafecampus.viewmodel.PedidosClienteViewModel
import com.tecsup.cafecampus.viewmodel.SesionViewModel

class MainActivity : ComponentActivity() {

    private val app by lazy { application as CafeApp }

    private val factory by lazy {
        CafeViewModelFactory(app.repository, app.preferenciasStore)
    }

    private val sesionViewModel: SesionViewModel by viewModels { factory }
    private val menuViewModel: MenuViewModel by viewModels { factory }
    private val pedidosClienteViewModel: PedidosClienteViewModel by viewModels { factory }
    private val panelAdminViewModel: PanelAdminViewModel by viewModels { factory }
    private val pedidosAdminViewModel: PedidosAdminViewModel by viewModels { factory }
    private val menuAdminViewModel: MenuAdminViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modoOscuro by app.preferenciasStore.modoOscuro.collectAsState(initial = false)
            val notificaciones by app.preferenciasStore.notificaciones.collectAsState(initial = true)
            val recibirPedidos by app.preferenciasStore.recibirPedidos.collectAsState(initial = true)
            val rolSeleccionado by sesionViewModel.rolSeleccionado.collectAsState()

            CafeTheme(
                rol = rolSeleccionado,
                darkTheme = modoOscuro
            ) {
                NavCafe(
                    sesionViewModel = sesionViewModel,
                    menuViewModel = menuViewModel,
                    pedidosClienteViewModel = pedidosClienteViewModel,
                    panelAdminViewModel = panelAdminViewModel,
                    pedidosAdminViewModel = pedidosAdminViewModel,
                    menuAdminViewModel = menuAdminViewModel,
                    preferenciasStore = app.preferenciasStore,
                    modoOscuro = modoOscuro,
                    notificaciones = notificaciones,
                    recibirPedidos = recibirPedidos
                )
            }
        }
    }
}
