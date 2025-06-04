
package com.example.deliciousfood.ui.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.deliciousfood.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (!BuildConfig.DEBUG) return
        Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
