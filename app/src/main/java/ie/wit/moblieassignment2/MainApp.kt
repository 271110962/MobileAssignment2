package ie.wit.moblieassignment2

import android.app.Application
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp: Application(), AnkoLogger {

    val memos = MyDatabaseHelper(this)

    override fun onCreate() {
        super.onCreate()
        info("Memo started")
    }
}