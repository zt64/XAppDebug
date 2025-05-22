package tw.idv.palatis.xappdebug2

import android.os.Process
import android.system.ErrnoException
import android.system.Os
import tw.idv.palatis.xappdebug2.Constants.CONFIG_PATH_FORMAT
import java.io.File
import java.io.IOException
import java.util.Locale

object Configuration {
    @JvmStatic
    fun add(packageName: String) {
        try {
            val path =
                String.format(
                    Locale.getDefault(),
                    CONFIG_PATH_FORMAT,
                    Process.myUserHandle().hashCode(),
                    packageName,
                )
            val file = File(path)
            val parent1 = File(file.parent)
            val parent2 = File(parent1.parent)
            parent1.mkdirs()
            Os.chmod(parent1.path, 493)
            Os.chmod(parent2.path, 493)
            file.createNewFile()
        } catch (ignored: ErrnoException) {
        } catch (ignored: IOException) {
        }
    }

    @JvmStatic
    fun remove(packageName: String) {
        val path =
            String.format(
                Locale.getDefault(),
                CONFIG_PATH_FORMAT,
                Process.myUserHandle().hashCode(),
                packageName,
            )
        File(path).delete()
    }

    @JvmStatic
    fun isEnabled(packageName: String): Boolean {
        val path =
            String.format(
                Locale.getDefault(),
                CONFIG_PATH_FORMAT,
                Process.myUserHandle().hashCode(),
                packageName,
            )
        return File(path).exists()
    }
}
