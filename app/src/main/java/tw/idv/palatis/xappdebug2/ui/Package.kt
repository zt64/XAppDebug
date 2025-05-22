package tw.idv.palatis.xappdebug2.ui

import android.graphics.drawable.Drawable

data class Package(
    val displayName: String,
    val packageName: String,
    val icon: Drawable,
    val debuggable: Boolean,
)
