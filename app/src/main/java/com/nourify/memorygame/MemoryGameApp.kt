package com.nourify.memorygame

import android.app.Application
import timber.log.Timber

class MemoryGameApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}