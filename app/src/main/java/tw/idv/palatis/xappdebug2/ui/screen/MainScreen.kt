package tw.idv.palatis.xappdebug2.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import tw.idv.palatis.xappdebug2.R
import tw.idv.palatis.xappdebug2.ui.SortOption
import tw.idv.palatis.xappdebug2.viewmodel.AppsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val vm = remember { AppsViewModel() }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    // TODO: REPLACE
    LaunchedEffect(Unit) {
        vm.updatePackageList(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                @rush
                title = { Text("GuhDebug2") },
                actions = {
                    Box {
                        var expanded by rememberSaveable { mutableStateOf(false) }

                        IconButton(
                            onClick = { expanded = true },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_sort_24),
                                contentDescription = "Sort",
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                Row {
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = "Sort By",
                                    )
                                }

                                Column(
                                    modifier = Modifier.selectableGroup(),
                                ) {
                                    SortOption.entries.forEach { sort ->
                                        Row(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .height(56.dp)
                                                    .selectable(
                                                        selected = false,
                                                        onClick = { vm.sort(sort) },
                                                        role = Role.RadioButton,
                                                    ).padding(horizontal = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            RadioButton(
                                                selected = false,
                                                onClick = null, // null recommended for accessibility with screen readers
                                            )

                                            Text(
                                                modifier = Modifier.padding(start = 16.dp),
                                                text = sort.label,
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        PullToRefreshBox(
            modifier =
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize(),
            isRefreshing = uiState == AppsViewModel.UiState.Loading,
            onRefresh = vm::refresh,
        ) {
            val packages by vm.installedPackages.collectAsState()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        FilterChip(
                            selected = false,
                            label = { Text("System") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.outline_android_24),
                                    contentDescription = "Debuggable",
                                )
                            },
                            onClick = {},
                        )

                        FilterChip(
                            selected = false,
                            label = { Text("Debuggable") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.outline_bug_report_24),
                                    contentDescription = "Debuggable",
                                )
                            },
                            onClick = {},
                        )
                    }
                }

                items(packages) { pkg ->
                    Row(
                        modifier =
                            Modifier
                                .clickable {
                                }.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier.size(42.dp),
                            bitmap = pkg.icon.toBitmap().asImageBitmap(),
                            contentDescription = "Info",
                        )

                        Column(
                            modifier =
                                Modifier
                                    .padding(horizontal = 4.dp)
                                    .weight(1f),
                        ) {
                            Text(
                                text = pkg.displayName,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = pkg.packageName,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Switch(
                            checked = pkg.debuggable,
                            onCheckedChange = {
                                vm.setDebuggable(pkg, it)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Loaded() {
}
