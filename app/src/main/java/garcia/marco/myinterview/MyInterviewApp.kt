package garcia.marco.myinterview

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyInterviewApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}