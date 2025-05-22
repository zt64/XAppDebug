package tw.idv.palatis.xappdebug2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun XposedScreen() {
    Scaffold(
        topBar = {
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
            )

            Text(
                text = "Xposed permission missing",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = "Please grant Xposed permission to this app.",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
