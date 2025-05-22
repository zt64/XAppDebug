package tw.idv.palatis.xappdebug2

import android.app.Application
import android.util.Log
import androidx.annotation.Keep
import tw.idv.palatis.xappdebug2.Constants.LOG_TAG

class MainApplication : Application() {
    companion object {
        @JvmStatic
        @get:Keep
        val activeXposedVersion: Int
            get() {
                Log.d(LOG_TAG, "Xposed framework is inactive.")
                return -1
            }
    }
}
