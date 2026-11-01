package com.tecsup.footballaianalyst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.footballaianalyst.data.remote.ApiFootballApi
import com.tecsup.footballaianalyst.data.remote.FootballDataApi
import com.tecsup.footballaianalyst.data.repository.FootballRepositoryImpl
import com.tecsup.footballaianalyst.data.server.LocalWebServer
import com.tecsup.footballaianalyst.ui.navigation.NavFootball
import com.tecsup.footballaianalyst.ui.theme.FootballAiTheme
import com.tecsup.footballaianalyst.viewmodel.FootballViewModel
import com.tecsup.footballaianalyst.viewmodel.MatchDetailViewModel
import com.tecsup.footballaianalyst.viewmodel.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private var localWebServer: LocalWebServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        enableEdgeToEdge()

        val app = application as FootballAiApp

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        val apiFootballRetrofit = Retrofit.Builder()
            .baseUrl("https://v3.football.api-sports.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballApi::class.java)

        val footballDataRetrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataApi::class.java)

        val repository = FootballRepositoryImpl(apiFootballRetrofit, footballDataRetrofit)

        // Inicia el Servidor Web Local para acceso desde navegador Chrome en http://localhost:8080
        localWebServer = LocalWebServer(repository, 8080).apply {
            start()
        }

        val footballVm by viewModels<FootballViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return FootballViewModel(repository, app.apiKeyStore) as T
                }
            }
        }

        val matchDetailVm by viewModels<MatchDetailViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MatchDetailViewModel(repository, app.apiKeyStore) as T
                }
            }
        }

        val settingsVm by viewModels<SettingsViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return SettingsViewModel(app.apiKeyStore) as T
                }
            }
        }

        setContent {
            FootballAiTheme {
                NavFootball(
                    footballVm = footballVm,
                    matchDetailVm = matchDetailVm,
                    settingsVm = settingsVm
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        localWebServer?.stop()
    }
}
