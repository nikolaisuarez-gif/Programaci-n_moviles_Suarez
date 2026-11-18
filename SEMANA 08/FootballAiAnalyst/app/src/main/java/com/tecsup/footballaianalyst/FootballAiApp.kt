package com.suarez.footballaianalyst

import android.app.Application
import com.suarez.footballaianalyst.data.prefs.ApiKeyStore

class FootballAiApp : Application() {
    lateinit var apiKeyStore: ApiKeyStore
        private set

    override fun onCreate() {
        super.onCreate()
        apiKeyStore = ApiKeyStore(this)
    }
}
