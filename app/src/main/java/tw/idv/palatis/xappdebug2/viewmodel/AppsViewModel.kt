package tw.idv.palatis.xappdebug2.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tw.idv.palatis.xappdebug2.ui.Package
import tw.idv.palatis.xappdebug2.ui.SortOption

class AppsViewModel : ViewModel() {
    private val _installedPackages = MutableStateFlow<List<Package>>(emptyList())
    val installedPackages = _installedPackages.asStateFlow()

    private var _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var sortOption: SortOption = SortOption.NAME

    sealed interface UiState {
        data class Loaded(
            val packages: List<Package>,
            val sortOption: SortOption,
        ) : UiState

        data object Loading : UiState

        data object Error
    }

    fun refresh() {
        // TODO
    }

    fun updatePackageList(context: Context) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            val pm = context.packageManager
            val packages =
                pm
                    .getInstalledPackages(0)
                    .map { pkgInfo ->
                        val appInfo = pkgInfo.applicationInfo!!
                        val displayName = pm.getApplicationLabel(appInfo).toString()
                        val icon: Drawable = pm.getApplicationIcon(appInfo)
                        Package(
                            packageName = pkgInfo.packageName,
                            displayName = displayName,
                            icon = icon,
                            debuggable = false,
                        )
                    }
            val sortedPackages = sortPackages(packages, sortOption)
            _installedPackages.emit(sortedPackages)
            _uiState.emit(UiState.Loaded(sortedPackages, sortOption))
        }
    }

    fun setDebuggable(
        pkg: Package,
        value: Boolean,
    ) {
        // TODO
    }

    fun sort(sort: SortOption) {
        sortOption = sort
        val packages = _installedPackages.value
        val sortedPackages = sortPackages(packages, sortOption)
        viewModelScope.launch {
            _installedPackages.emit(sortedPackages)
            _uiState.emit(UiState.Loaded(sortedPackages, sortOption))
        }
    }

    private fun sortPackages(
        packages: List<Package>,
        sort: SortOption,
    ): List<Package> =
        when (sort) {
            SortOption.NAME -> packages.sortedBy { it.displayName }
            else -> TODO()
        }
}
