package com.contoh.scentapp.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contoh.scentapp.data.model.OrderStatus
import com.contoh.scentapp.ui.theme.*

private data class DemoOrder(
    val id            : String,
    val productName   : String,
    val volume        : String,
    val totalStr      : String,
    val date          : String,
    val status        : OrderStatus,
    val paymentMethod : String = "Transfer"
)

@Composable
fun OrderHistoryScreen(
    onBack             : () -> Unit,
    onOrderDetailClick : (String) -> Unit
) {
    val tabs = listOf("Semua", "Belum Bayar", "Diproses", "Dikirim", "Selesai", "Batal")
    var selectedTab by remember { mutableStateOf("Semua") }

    val allOrders = listOf(
        DemoOrder("99283", "NOIR OBSCUR",    "50ML",  "Rp 255.000",   "10 Juni 2026", OrderStatus.DIKIRIM,           "Transfer"),
        DemoOrder("99284", "SANTAL BLANC",   "100ML", "Rp 230.000",   "8 Juni 2026",  OrderStatus.DIKEMAS,           "COD"),
        DemoOrder("99285", "AMBER ROSE",     "50ML",  "Rp 195.000",   "5 Juni 2026",  OrderStatus.DELIVERED,         "Transfer"),
        DemoOrder("99286", "CITRUS ÉTHÉRÉ",  "100ML", "Rp 640.000",   "1 Juni 2026",  OrderStatus.SELESAI,           "Transfer"),
        DemoOrder("99287", "OUD IMMEMORIAL", "100ML", "Rp 275.000",   "28 Mei 2026",  OrderStatus.MENUNGGU_KONFIRMASI,"Transfer"),
        DemoOrder("99288", "SANTAL BLANC",   "10ML",  "Rp 80.000",    "20 Mei 2026",  OrderStatus.CANCELLED,         "COD")
    )

    val filteredOrders = when (selectedTab) {
        "Belum Bayar" -> allOrders.filter { it.status == OrderStatus.WAITING_PAYMENT || it.status == OrderStatus.MENUNGGU_KONFIRMASI }
        "Diproses"    -> allOrders.filter { it.status in listOf(OrderStatus.DALAM_PROSES, OrderStatus.DIKEMAS) }
        "Dikirim"     -> allOrders.filter { it.status == OrderStatus.DIKIRIM }
        "Selesai"     -> allOrders.filter { it.status in listOf(OrderStatus.DELIVERED, OrderStatus.SELESAI) }
        "Batal"       -> allOrders.filter { it.status == OrderStatus.CANCELLED }
        else          -> allOrders
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ── Top Bar ───────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = ScentWhite,
                    modifier = Modifier.size(24.dp).clickable(onClick = onBack))
                Text("RIWAYAT PESANAN", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, letterSpacing = 2.sp), color = ScentWhite)
                Spacer(Modifier.size(24.dp))
            }

            // ── Tab Filter ────────────────────────────────────────────────────
            LazyRow(
                contentPadding         = PaddingValues(horizontal = 20.dp),
                horizontalArrangement  = Arrangement.spacedBy(8.dp),
                modifier               = Modifier.padding(bottom = 16.dp)
            ) {
                items(tabs) { tab ->
                    val isSelected = tab == selectedTab
                    val count = when (tab) {
                        "Semua" -> allOrders.size
                        "Belum Bayar" -> allOrders.count { it.status == OrderStatus.WAITING_PAYMENT || it.status == OrderStatus.MENUNGGU_KONFIRMASI }
                        "Diproses"    -> allOrders.count { it.status in listOf(OrderStatus.DALAM_PROSES, OrderStatus.DIKEMAS) }
                        "Dikirim"     -> allOrders.count { it.status == OrderStatus.DIKIRIM }
                        "Selesai"     -> allOrders.count { it.status in listOf(OrderStatus.DELIVERED, OrderStatus.SELESAI) }
                        "Batal"       -> allOrders.count { it.status == OrderStatus.CANCELLED }
                        else -> 0
                    }
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color(0xFF222222) else Color.Transparent)
                            .border(1.dp, if (isSelected) ScentDivider else Color.Transparent, RoundedCornerShape(20.dp))
                            .clickable { selectedTab = tab }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(tab, color = if (isSelected) ScentWhite else ScentTextMuted,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal))
                            if (count > 0 && tab != "Semua") {
                                Spacer(Modifier.width(4.dp))
                                Box(modifier = Modifier.size(16.dp).clip(RoundedCornerShape(8.dp)).background(ScentGold.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                                    Text("$count", style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp, color = ScentGold))
                                }
                            }
                        }
                    }
                }
            }

            // ── Order List ────────────────────────────────────────────────────
            if (filteredOrders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShoppingBag, null, tint = ScentTextLabel, modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(16.dp))
                        Text("Tidak ada pesanan", style = MaterialTheme.typography.titleMedium.copy(color = ScentTextMuted))
                    }
                }
            } else {
                LazyColumn(
                    contentPadding        = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement   = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredOrders, key = { it.id }) { order ->
                        OrderHistoryCard(
                            order   = order,
                            onClick = { onOrderDetailClick(order.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryCard(order: DemoOrder, onClick: () -> Unit) {
    val (statusColor, statusBg) = statusStyle(order.status)
    val statusIcon = statusIcon(order.status)

    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF161616)).border(0.5.dp, ScentDivider, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick).padding(16.dp)
    ) {
        // Header row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(order.date, style = MaterialTheme.typography.labelSmall.copy(color = ScentTextMuted, fontSize = 10.sp))
            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(statusBg).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(statusIcon, null, tint = statusColor, modifier = Modifier.size(10.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(order.status.label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, letterSpacing = 0.5.sp, color = statusColor, fontWeight = FontWeight.Bold))
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Product info
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(52.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF1A1A1A)).border(0.5.dp, ScentDivider, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.LocalFlorist, null, tint = ScentGold.copy(alpha = 0.6f), modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("#SCNT-${order.id}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 1.sp, color = ScentTextLabel))
                Spacer(Modifier.height(3.dp))
                Text(order.productName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp), color = ScentWhite)
                Text("${order.volume} • ${order.paymentMethod}", style = MaterialTheme.typography.bodySmall.copy(color = ScentTextMuted, fontSize = 11.sp))
            }
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = ScentDivider, thickness = 0.5.dp)
        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Total Pembayaran", style = MaterialTheme.typography.bodySmall.copy(color = ScentTextMuted))
            Text(order.totalStr, style = MaterialTheme.typography.titleMedium.copy(color = ScentWhite, fontWeight = FontWeight.Bold))
        }

        // Action button for DIKIRIM orders
        if (order.status == OrderStatus.DIKIRIM) {
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                    .background(ScentWhite).padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("LIHAT DETAIL & KONFIRMASI", style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold, color = ScentBlack))
            }
        }
    }
}

private fun statusStyle(status: OrderStatus): Pair<Color, Color> = when (status) {
    OrderStatus.MENUNGGU_KONFIRMASI, OrderStatus.WAITING_PAYMENT -> Pair(Color(0xFFD4A853), Color(0xFFD4A853).copy(alpha = 0.1f))
    OrderStatus.DALAM_PROSES, OrderStatus.DIKEMAS               -> Pair(Color(0xFF2196F3), Color(0xFF2196F3).copy(alpha = 0.1f))
    OrderStatus.DIKIRIM                                          -> Pair(Color(0xFF9C27B0), Color(0xFF9C27B0).copy(alpha = 0.1f))
    OrderStatus.DELIVERED, OrderStatus.SELESAI                  -> Pair(Color(0xFF4CAF50), Color(0xFF4CAF50).copy(alpha = 0.1f))
    OrderStatus.CANCELLED, OrderStatus.TIDAK_SAMPAI             -> Pair(Color(0xFFCF6679), Color(0xFFCF6679).copy(alpha = 0.1f))
    else                                                         -> Pair(ScentTextMuted, ScentSearchBg)
}

private fun statusIcon(status: OrderStatus): ImageVector = when (status) {
    OrderStatus.MENUNGGU_KONFIRMASI                              -> Icons.Default.HourglassEmpty
    OrderStatus.DALAM_PROSES, OrderStatus.DIKEMAS               -> Icons.Default.Inventory
    OrderStatus.DIKIRIM                                          -> Icons.Default.LocalShipping
    OrderStatus.DELIVERED, OrderStatus.SELESAI                  -> Icons.Default.CheckCircle
    OrderStatus.CANCELLED, OrderStatus.TIDAK_SAMPAI             -> Icons.Default.Cancel
    else                                                         -> Icons.Default.ShoppingBag
}
