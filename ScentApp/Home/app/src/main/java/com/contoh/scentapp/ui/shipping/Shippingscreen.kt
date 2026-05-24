package com.contoh.scentapp.ui.shipping

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contoh.scentapp.data.model.ShippingOption
import com.contoh.scentapp.data.repository.CartRepository
import com.contoh.scentapp.ui.theme.*

@Composable
fun ShippingScreen(
    onBack    : () -> Unit = {},
    onConfirm : () -> Unit = {}
) {
    val repository    = CartRepository.getInstance()
    val listState     = rememberLazyListState()

    // rememberSaveable: pilihan kurir bertahan saat rotasi
    var selectedId by rememberSaveable { mutableStateOf("jnt") }

    val shippingOptions = repository.shippingOptions
    val selectedOption  = shippingOptions.find { it.id == selectedId } ?: shippingOptions.first()

    // Hitung subtotal dari cart
    val cartItems   = repository.cartItems
    val subtotal    = 1_450_000 // demo value
    val shippingFee = selectedOption.price
    val total       = subtotal + shippingFee

    fun formatRp(value: Int) = "Rp ${"%,d".format(value).replace(",", ".")}"

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
                        imageVector        = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint               = ScentWhite,
                        modifier           = Modifier
                            .size(24.dp)
                            .clickable(onClick = onBack)
                    )
                    Text(
                        text  = "SCENT",
                        style = MaterialTheme.typography.titleLarge.copy(
                            letterSpacing = 6.sp,
                            fontSize      = 18.sp,
                            fontWeight    = FontWeight.Bold
                        ),
                        color = ScentWhite
                    )
                    Text(
                        text  = "Pilih Kurir",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ScentTextMuted
                        )
                    )
                }
            }

            // ── Header ────────────────────────────────────────────────────────
            item(key = "header") {
                Column(
                    modifier = Modifier.padding(
                        start  = 20.dp,
                        end    = 20.dp,
                        top    = 8.dp,
                        bottom = 28.dp
                    )
                ) {
                    Text(
                        text  = "Metode Pengiriman",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize   = 28.sp
                        ),
                        color = ScentWhite
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text  = "Pilih layanan kurir yang paling sesuai untuk pesanan Anda.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color      = ScentTextMuted,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // ── Shipping Options ──────────────────────────────────────────────
            items(
                count = shippingOptions.size,
                key   = { shippingOptions[it].id }
            ) { index ->
                val option     = shippingOptions[index]
                val isSelected = option.id == selectedId

                val borderColor by animateColorAsState(
                    targetValue   = if (isSelected) ScentWhite else Color.Transparent,
                    animationSpec = tween(200),
                    label         = "border_${option.id}"
                )
                val bgColor by animateColorAsState(
                    targetValue   = if (isSelected) ScentSearchBg else Color(0xFF161616),
                    animationSpec = tween(200),
                    label         = "bg_${option.id}"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable { selectedId = option.id }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon kurir
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF222222)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = iconForType(option.iconType),
                            contentDescription = option.name,
                            tint               = ScentWhite,
                            modifier           = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    // Nama + estimasi
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text  = option.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 15.sp
                                ),
                                color = ScentWhite
                            )
                            Spacer(Modifier.width(8.dp))
                            // Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF2A2A2A))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text  = option.badge,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize      = 8.sp,
                                        letterSpacing = 1.sp,
                                        color         = ScentTextMuted
                                    )
                                )
                            }
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text  = option.estimasi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ScentTextMuted
                            )
                        )
                    }

                    // Harga + radio
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text  = option.formattedPrice,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize   = 14.sp,
                                color      = ScentWhite
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        RadioButton(
                            selected = isSelected,
                            onClick  = { selectedId = option.id },
                            colors   = RadioButtonDefaults.colors(
                                selectedColor   = ScentWhite,
                                unselectedColor = ScentTextMuted
                            )
                        )
                    }
                }
            }

            // ── Detail Pesanan ────────────────────────────────────────────────
            item(key = "order_detail") {
                Spacer(Modifier.height(24.dp))
                HorizontalDivider(color = ScentDivider, thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 20.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Text(
                        text  = "DETAIL PESANAN",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 2.sp,
                            color         = ScentTextLabel
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text  = "Subtotal Produk",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentTextMuted)
                        )
                        Text(
                            text  = formatRp(subtotal),
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentWhite)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text  = "Biaya Pengiriman",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentTextMuted)
                        )
                        Text(
                            text  = formatRp(shippingFee),
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentWhite)
                        )
                    }
                    HorizontalDivider(color = ScentDivider, thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 16.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            text  = "Total Tagihan",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color      = ScentWhite
                            )
                        )
                        Text(
                            text  = formatRp(total),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize   = 20.sp,
                                color      = ScentWhite
                            )
                        )
                    }
                }
            }
        }

        // ── Tombol Lanjutkan (sticky bottom) ──────────────────────────────────
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
                    .clickable(onClick = onConfirm)
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "LANJUTKAN KE PEMBAYARAN",
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

// ── Helper: icon berdasarkan tipe kurir ───────────────────────────────────────

@Composable
private fun iconForType(type: String): ImageVector = when (type) {
    "lightning" -> Icons.Default.FlashOn
    "plane"     -> Icons.Default.Flight
    "bike"      -> Icons.Default.DirectionsBike
    else        -> Icons.Default.LocalShipping
}