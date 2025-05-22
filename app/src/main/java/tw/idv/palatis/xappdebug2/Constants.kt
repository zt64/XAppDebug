package tw.idv.palatis.xappdebug2

object Constants {
    const val LOG_TAG: String = "XAppDebug"

    const val CONFIG_PATH_FORMAT: String = "/data/user_de/%d/" + BuildConfig.APPLICATION_ID + "/config/%s"

    const val SORT_ORDER_LABEL: Int = 0
    const val SORT_ORDER_PACKAGE_NAME: Int = 1
    const val SORT_ORDER_INSTALL_TIME: Int = 2
    const val SORT_ORDER_UPDATE_TIME: Int = 3

    const val PREF_KEY_SORT_ORDER: String = "preference_sort_order"
    const val PREF_KEY_SHOW_DEBUGGABLE_FIRST: String = "preference_show_debuggable_first"
    const val PREF_KEY_SHOW_SYSTEM: String = "preference_show_system"
    const val PREF_KEY_SHOW_DEBUG: String = "preference_show_debug"
}
