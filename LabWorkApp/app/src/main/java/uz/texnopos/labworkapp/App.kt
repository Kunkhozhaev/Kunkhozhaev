package uz.texnopos.labworkapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.texnopos.labworkapp.di.networkModule
import uz.texnopos.labworkapp.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        val modules = listOf(networkModule, viewModelModule)
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(modules)
        }
    }

    companion object {
        private lateinit var appInstance: App
        fun getAppInstance(): App {
            return appInstance
        }
    }
}