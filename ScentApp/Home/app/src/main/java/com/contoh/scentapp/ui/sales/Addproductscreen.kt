package com.contoh.scentapp.ui.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contoh.scentapp.data.model.SalesProduct
import com.contoh.scentapp.ui.theme.*

// ── Pilihan aroma ─────────────────────────────────────────────────────────────

private val aromaFamilies = listOf("Woody", "Floral", "Oriental", "Citrus", "Gourmand", "Aquatic")
private val sizeOptions   = listOf("30", "50", "100")

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddProductScreen(
    onBack     : () -> Unit = {},
    onSave     : (SalesProduct) -> Unit = {}
) {
    // rememberSaveable: semua field bertahan saat rotasi
    var namaParfum      by rememberSaveable { mutableStateOf("") }
    var harga           by rememberSaveable { mutableStateOf("") }
    var selectedAroma   by rememberSaveable { mutableStateOf("Woody") }
    var jumlahStok      by rememberSaveable { mutableStateOf("") }
    var selectedSize    by rememberSaveable { mutableStateOf("50") }
    var deskripsi       by rememberSaveable { mutableStateOf("") }
    var aromaChips      by rememberSaveable { mutableStateOf(listOf("OUD", "BERGAMOT")) }
    var newChipInput    by rememberSaveable { mutableStateOf("") }
    var showAromaMenu   by rememberSaveable { mutableStateOf(false) }
    var showChipInput   by rememberSaveable { mutableStateOf(false) }

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScentBlack)
    ) {
        LazyColumn(
            state          = listState,
            modifier       = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // ── Top Bar ───────────────────────────────────────────────────────
            item(key = "topbar") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint               = ScentWhite,
                        modifier           = Modifier
                            .size(24.dp)
                            .clickable(onClick = onBack)
                    )
                    Text(
                        text  = "SCENT",
                        style = MaterialTheme.typography.titleLarge.copy(
                            letterSpacing = 6.sp, fontSize = 18.sp, fontWeight = FontWeight.Bold
                        ),
                        color = ScentWhite
                    )
                    Icon(
                        imageVector        = Icons.Default.Help,
                        contentDescription = null,
                        tint               = ScentTextMuted,
                        modifier           = Modifier.size(22.dp)
                    )
                }
            }

            // ── Header ────────────────────────────────────────────────────────
            item(key = "header") {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text  = "MANAJEMEN INVENTARIS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 2.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text  = "Tambah Produk Baru",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold, fontSize = 26.sp
                        ),
                        color = ScentWhite
                    )
                }
            }

            // ── Upload Gambar ─────────────────────────────────────────────────
            item(key = "upload") {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ScentBlack)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector        = Icons.Default.AddAPhoto,
                            contentDescription = "Upload",
                            tint               = ScentTextMuted,
                            modifier           = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text  = "UNGGAH GAMBAR UTAMA",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize      = 10.sp,
                                letterSpacing = 2.sp,
                                color         = ScentTextMuted
                            )
                        )
                    }
                }
            }

            // ── Nama Parfum ───────────────────────────────────────────────────
            item(key = "nama") {
                ProductFormField(
                    label       = "NAMA PARFUM",
                    value       = namaParfum,
                    onChange    = { namaParfum = it },
                    placeholder = "contoh: Noir Éphémère",
                    modifier    = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            // ── Harga ─────────────────────────────────────────────────────────
            item(key = "harga") {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text  = "HARGA (RP)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, ScentDivider, RoundedCornerShape(8.dp))
                            .padding(horizontal = 14.dp, vertical = 14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text  = "Rp",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ScentTextMuted
                                )
                            )
                            Spacer(Modifier.width(8.dp))
                            BasicTextField(
                                value           = harga,
                                onValueChange   = { harga = it },
                                textStyle       = MaterialTheme.typography.bodyMedium.copy(
                                    color = ScentWhite, fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                cursorBrush     = SolidColor(ScentGold),
                                singleLine      = true,
                                modifier        = Modifier.fillMaxWidth(),
                                decorationBox   = { inner ->
                                    if (harga.isEmpty()) {
                                        Text(
                                            text  = "2.450.000",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = ScentTextMuted, fontSize = 16.sp
                                            )
                                        )
                                    }
                                    inner()
                                }
                            )
                        }
                    }
                }
            }

            // ── Dropdown Wangi ────────────────────────────────────────────────
            item(key = "wangi") {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text  = "WANGI (OLFACTORY FAMILY)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, ScentDivider, RoundedCornerShape(8.dp))
                                .clickable { showAromaMenu = !showAromaMenu }
                                .padding(horizontal = 14.dp, vertical = 14.dp)
                        ) {
                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically
                            ) {
                                Text(
                                    text  = selectedAroma,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ScentWhite, fontSize = 16.sp
                                    )
                                )
                                Icon(
                                    imageVector        = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint               = ScentTextMuted,
                                    modifier           = Modifier.size(20.dp)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded         = showAromaMenu,
                            onDismissRequest = { showAromaMenu = false },
                            modifier         = Modifier.background(ScentBlack)
                        ) {
                            aromaFamilies.forEach { aroma ->
                                DropdownMenuItem(
                                    text    = {
                                        Text(aroma,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = ScentWhite))
                                    },
                                    onClick = {
                                        selectedAroma = aroma
                                        showAromaMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ── Jumlah Stok ───────────────────────────────────────────────────
            item(key = "stok") {
                ProductFormField(
                    label        = "JUMLAH STOK",
                    value        = jumlahStok,
                    onChange     = { jumlahStok = it },
                    placeholder  = "48",
                    keyboardType = KeyboardType.Number,
                    modifier     = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            // ── Ukuran (ML) ───────────────────────────────────────────────────
            item(key = "ukuran") {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text  = "UKURAN (ML)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        sizeOptions.forEach { size ->
                            val isSelected = size == selectedSize
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) ScentWhite else Color.Transparent
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) ScentWhite else ScentDivider,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedSize = size }
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text  = size,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize      = 12.sp,
                                        letterSpacing = 1.sp,
                                        fontWeight    = FontWeight.Bold,
                                        color         = if (isSelected) ScentBlack else ScentTextMuted
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // ── Deskripsi + Aroma Notes ───────────────────────────────────────
            item(key = "deskripsi_section") {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(ScentBlack)
                        .padding(16.dp)
                ) {
                    // Deskripsi Wangi
                    Text(
                        text  = "DESKRIPSI WANGI",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    BasicTextField(
                        value         = deskripsi,
                        onValueChange = { deskripsi = it },
                        textStyle     = MaterialTheme.typography.bodyMedium.copy(
                            color = ScentWhite, lineHeight = 22.sp
                        ),
                        cursorBrush   = SolidColor(ScentGold),
                        minLines      = 3,
                        modifier      = Modifier.fillMaxWidth(),
                        decorationBox = { inner ->
                            if (deskripsi.isEmpty()) {
                                Text(
                                    text  = "Gambarkan jiwa dari wewangian ini...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = ScentTextMuted, lineHeight = 22.sp
                                    )
                                )
                            }
                            inner()
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    // Catatan Aroma
                    Text(
                        text  = "CATATAN AROMA UTAMA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(12.dp))

                    // Chips aroma yang sudah ditambah
                    androidx.compose.foundation.layout.FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement   = Arrangement.spacedBy(8.dp)
                    ) {
                        aromaChips.forEach { chip ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(ScentSearchBg)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text  = chip,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize      = 10.sp,
                                            letterSpacing = 1.sp,
                                            color         = ScentWhite
                                        )
                                    )
                                    Icon(
                                        imageVector        = Icons.Default.Close,
                                        contentDescription = "Hapus",
                                        tint               = ScentTextMuted,
                                        modifier           = Modifier
                                            .size(12.dp)
                                            .clickable {
                                                aromaChips = aromaChips.filter { it != chip }
                                            }
                                    )
                                }
                            }
                        }

                        // Input chip baru
                        if (showChipInput) {
                            BasicTextField(
                                value         = newChipInput,
                                onValueChange = { newChipInput = it },
                                textStyle     = MaterialTheme.typography.labelSmall.copy(
                                    color = ScentWhite, fontSize = 12.sp
                                ),
                                cursorBrush   = SolidColor(ScentGold),
                                singleLine    = true,
                                modifier      = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(1.dp, ScentGold, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .width(100.dp)
                            )
                        }

                        // Tombol tambah aroma
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, ScentDivider, RoundedCornerShape(6.dp))
                                .clickable {
                                    if (showChipInput && newChipInput.isNotBlank()) {
                                        aromaChips    = aromaChips + newChipInput.uppercase()
                                        newChipInput  = ""
                                        showChipInput = false
                                    } else {
                                        showChipInput = !showChipInput
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text  = if (showChipInput && newChipInput.isNotBlank())
                                    "✓ SIMPAN" else "+ TAMBAH AROMA",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize      = 10.sp,
                                    letterSpacing = 1.sp,
                                    color         = ScentTextMuted
                                )
                            )
                        }
                    }
                }
            }
        }

        // ── Tombol Tambah Produk (sticky bawah) ──────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(ScentBlack)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(ScentWhite)
                    .clickable {
                        if (namaParfum.isNotBlank()) {
                            val newProduct = SalesProduct(
                                id          = System.currentTimeMillis().toInt(),
                                name        = namaParfum.uppercase(),
                                aromaFamily = selectedAroma.uppercase(),
                                volume      = "${selectedSize}ML",
                                stockStatus = if ((jumlahStok.toIntOrNull() ?: 0) > 5)
                                    "TERSEDIA" else "STOK MENIPIS",
                                price       = harga.replace(".", "").toIntOrNull() ?: 0,
                                stock       = jumlahStok.toIntOrNull() ?: 0
                            )
                            onSave(newProduct)
                            onBack()
                        }
                    }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "TAMBAH PRODUK",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize      = 12.sp,
                        letterSpacing = 2.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = ScentBlack
                    )
                )
            }
        }
    }
}

// ── Form Field Helper ─────────────────────────────────────────────────────────

@Composable
private fun ProductFormField(
    label        : String,
    value        : String,
    onChange     : (String) -> Unit,
    placeholder  : String       = "",
    keyboardType : KeyboardType = KeyboardType.Text,
    modifier     : Modifier     = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp, letterSpacing = 1.5.sp, color = ScentTextLabel
            )
        )
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, ScentDivider, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            BasicTextField(
                value           = value,
                onValueChange   = onChange,
                textStyle       = MaterialTheme.typography.bodyMedium.copy(
                    color = ScentWhite, fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                cursorBrush     = SolidColor(ScentGold),
                singleLine      = true,
                modifier        = Modifier.fillMaxWidth(),
                decorationBox   = { inner ->
                    if (value.isEmpty()) {
                        Text(
                            text  = placeholder,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ScentTextMuted, fontSize = 16.sp
                            )
                        )
                    }
                    inner()
                }
            )
        }
    }
}