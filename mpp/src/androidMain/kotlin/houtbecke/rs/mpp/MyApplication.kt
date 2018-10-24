package houtbecke.rs.mpp

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Application init")
    }
}