package pucaberta.pucminas.com

import android.app.Application
import di.dataModule
import di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pucaberta.pucminas.core.di.coreModule
import pucaberta.pucminas.presentation.di.presentationModule


class PucAbertApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@PucAbertApp)
            modules(
                listOf(
                    domainModule,
                    dataModule,
                    presentationModule,
                    coreModule
                )
            )
        }
    }
}