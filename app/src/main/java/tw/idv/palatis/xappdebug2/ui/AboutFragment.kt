package tw.idv.palatis.xappdebug2.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import tw.idv.palatis.xappdebug2.BuildConfig
import tw.idv.palatis.xappdebug2.MainApplication
import tw.idv.palatis.xappdebug2.R

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val button = root.findViewById<Button>(R.id.github)
        button.setOnClickListener(
            View.OnClickListener { v: View? ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        getString(R.string.url_project_page).toUri(),
                    ),
                )
            },
        )

        val version = root.findViewById<TextView>(R.id.version)
        version.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        val xposed = MainApplication.activeXposedVersion
        if (xposed != -1) {
            val text = root.findViewById<TextView>(R.id.xposed)
            text.text = getString(R.string.xposed_version, xposed)
        }
        return root
    }
}
