package com.example.hyperbar

import android.app.Application
import com.example.hyperbar.data.dataModule
import com.example.hyperbar.data.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class HyperBarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HyperBarApplication)
            modules(
                listOf(
                    repositoryModule,
                    dataModule
                )
            )
        }
    }
}