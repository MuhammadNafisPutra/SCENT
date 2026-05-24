package com.contoh.scentapp.ui.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.scentapp.data.model.AromaFilter
import com.contoh.scentapp.data.model.UsageFilter
import com.contoh.scentapp.ui.theme.ScentBlack
import com.contoh.scentapp.ui.theme.ScentDivider
import com.contoh.scentapp.ui.theme.ScentGold
import com.contoh.scentapp.ui.theme.ScentSearchBg
import com.contoh.scentapp.ui.theme.ScentTextLabel
import com.contoh.scentapp.ui.theme.ScentTextMuted
import com.contoh.scentapp.ui.theme.ScentWhite

// ── Entry Point ───────────────────────────────────────────────────────────────

@Composable
fun SearchScreen(
    initialQuery : String = "",
    onBack       : () -> Unit,
    viewModel    : SearchViewModel = viewModel(factory = SearchViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var localQuery by rememberSaveable { mutableStateOf(initialQuery) }

    LaunchedEffect(localQuery) { viewModel.onQueryChange(localQuery) }

    val listState  = rememberLazyListState()
    val focusReq   = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusReq.requestFocus() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScentBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTopBar(onBack = onBack)
            LazyColumn(
                state          = listState,
                modifier       = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item(key = "search_input") {
                    SearchInputField(
                        query          = localQuery,
                        onChange       = { localQuery = it },
                        focusRequester = focusReq,
                        modifier       = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                    )
                }
                item(key = "aroma_header") {
                    FilterSectionLabel(
                        text     = "PROFIL AROMA",
                        modifier = Modifier.padding(
                            start  = 20.dp,
                            end    = 20.dp,
                            top    = 8.dp,
                            bottom = 12.dp
                        )
                    )
                }
                item(key = "aroma_chips") {
                    AromaChipGroup(
                        filters         = uiState.aromaFilters,
                        selectedFilters = uiState.selectedAromaFilters,
                        onToggle        = { viewModel.toggleAromaFilter(it) },
                        modifier        = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item(key = "usage_header") {
                    FilterSectionLabel(
                        text     = "PENGGUNAAN",
                        modifier = Modifier.padding(
                            start  = 20.dp,
                            end    = 20.dp,
                            top    = 28.dp,
                            bottom = 12.dp
                        )
                    )
                }
                item(key = "usage_buttons") {
                    UsageButtonGroup(
                        filters       = uiState.usageFilters,
                        selectedUsage = uiState.selectedUsage,
                        onToggle      = { viewModel.toggleUsageFilter(it) },
                        modifier      = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item(key = "results_summary") {
                    Spacer(Modifier.height(32.dp))
                    HorizontalDivider(color = ScentDivider, thickness = 0.5.dp)
                    ResultsSummary(
                        count     = uiState.resultCount,
                        hasActive = uiState.hasActiveFilters,
                        onClear   = {
                            viewModel.clearAllFilters()
                            localQuery = ""
                        },
                        modifier  = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ScentBlack)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .navigationBarsPadding()
            ) {
                ApplyFilterButton(
                    onClick = {
                        viewModel.applyFilters()
                        onBack()
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector        = Icons.Default.ArrowBack,
            contentDescription = "Kembali",
            tint               = ScentWhite,
            modifier           = Modifier
                .size(24.dp)
                .clickable(onClick = onBack)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text  = "SCENT",
            style = MaterialTheme.typography.titleLarge.copy(
                letterSpacing = 6.sp,
                fontSize      = 18.sp,
                fontWeight    = FontWeight.Bold
            ),
            color = ScentWhite
        )
    }
}

@Composable
private fun SearchInputField(
    query          : String,
    onChange       : (String) -> Unit,
    focusRequester : FocusRequester,
    modifier       : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, ScentDivider, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier              = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value         = query,
                onValueChange = onChange,
                singleLine    = true,
                cursorBrush   = SolidColor(ScentGold),
                textStyle     = MaterialTheme.typography.titleMedium.copy(
                    color         = ScentWhite,
                    fontSize      = 20.sp,
                    fontWeight    = FontWeight.Normal,
                    letterSpacing = 2.sp
                ),
                modifier      = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                decorationBox = { inner ->
                    if (query.isEmpty()) {
                        Text(
                            text  = "Cari esens Anda...",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color    = ScentTextMuted,
                                fontSize = 20.sp
                            )
                        )
                    }
                    inner()
                }
            )
            Icon(
                imageVector        = Icons.Default.Search,
                contentDescription = "Cari",
                tint               = ScentTextMuted,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun FilterSectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text     = text,
        modifier = modifier,
        style    = MaterialTheme.typography.labelSmall.copy(
            fontSize      = 10.sp,
            letterSpacing = 2.sp,
            color         = ScentTextLabel
        )
    )
}

@Composable
private fun AromaChipGroup(
    filters         : List<AromaFilter>,
    selectedFilters : Set<String>,
    onToggle        : (String) -> Unit,
    modifier        : Modifier = Modifier
) {
    val rows = filters.chunked(2)
    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { filter ->
                    AromaFilterChip(
                        label      = filter.label,
                        isSelected = filter.id in selectedFilters,
                        onClick    = { onToggle(filter.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AromaFilterChip(
    label      : String,
    isSelected : Boolean,
    onClick    : () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue   = if (isSelected) ScentSearchBg else Color.Transparent,
        animationSpec = tween(200),
        label         = "chipBg_$label"
    )
    val borderColor by animateColorAsState(
        targetValue   = if (isSelected) ScentWhite else ScentDivider,
        animationSpec = tween(200),
        label         = "chipBorder_$label"
    )
    val textColor by animateColorAsState(
        targetValue   = if (isSelected) ScentWhite else ScentTextMuted,
        animationSpec = tween(200),
        label         = "chipText_$label"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(50.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize      = 10.sp,
                letterSpacing = 1.5.sp,
                fontWeight    = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color         = textColor
            )
        )
    }
}

@Composable
private fun UsageButtonGroup(
    filters       : List<UsageFilter>,
    selectedUsage : String?,
    onToggle      : (String) -> Unit,
    modifier      : Modifier = Modifier
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter.id == selectedUsage
            val bgColor by animateColorAsState(
                targetValue   = if (isSelected) ScentGold.copy(alpha = 0.15f)
                else Color.Transparent,
                animationSpec = tween(200),
                label         = "usageBg_${filter.id}"
            )
            val borderColor by animateColorAsState(
                targetValue   = if (isSelected) ScentGold else ScentDivider,
                animationSpec = tween(200),
                label         = "usageBorder_${filter.id}"
            )
            val textColor by animateColorAsState(
                targetValue   = if (isSelected) ScentWhite else ScentTextMuted,
                animationSpec = tween(200),
                label         = "usageText_${filter.id}"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(bgColor)
                    .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                    .clickable { onToggle(filter.id) }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = filter.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize      = 11.sp,
                        letterSpacing = 2.sp,
                        fontWeight    = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color         = textColor
                    )
                )
            }
        }
    }
}

@Composable
private fun ResultsSummary(
    count     : Int,
    hasActive : Boolean,
    onClear   : () -> Unit,
    modifier  : Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text  = "HASIL DITEMUKAN",
            style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 2.sp,
                color         = ScentTextLabel,
                fontSize      = 10.sp
            )
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text  = "$count Produk",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp)
            )
            if (hasActive) {
                Row(
                    modifier          = Modifier.clickable(onClick = onClear),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text  = "HAPUS SEMUA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 1.5.sp,
                            color         = ScentTextMuted
                        )
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector        = Icons.Default.Close,
                        contentDescription = "Hapus filter",
                        tint               = ScentTextMuted,
                        modifier           = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ApplyFilterButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ScentWhite)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text  = "TERAPKAN FILTER",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize      = 12.sp,
                letterSpacing = 2.sp,
                fontWeight    = FontWeight.Bold,
                color         = ScentBlack
            )
        )
    }
}