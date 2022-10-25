package uz.gita.pirmuhammad.app

import android.app.Application
import uz.gita.pirmuhammad.data.local.pref.SharedPref
import uz.gita.pirmuhammad.data.repository.Impl.AppRepositoryImpl

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        AppRepositoryImpl.init()
    }
}