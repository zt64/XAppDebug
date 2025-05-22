package tw.idv.palatis.xappdebug2.xposed

import androidx.annotation.Keep
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import tw.idv.palatis.xappdebug2.BuildConfig
import tw.idv.palatis.xappdebug2.MainApplication

@Keep
class HookSelf : IXposedHookLoadPackage {
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if (BuildConfig.APPLICATION_ID != lpparam.packageName) return

        XposedHelpers.findAndHookMethod(
            MainApplication::class.java.getName(),
            lpparam.classLoader,
            "getActiveXposedVersion",
            XC_MethodReplacement.returnConstant(XposedBridge.getXposedVersion()),
        )
    }
}
