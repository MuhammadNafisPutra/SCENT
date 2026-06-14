package com.contoh.scentapp.ui.ordersuccess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contoh.scentapp.data.repository.CartRepository
import com.contoh.scentapp.ui.theme.*
import androidx.compose.ui.res.stringResource
import com.contoh.scentapp.R

@Composable
fun OrderSuccessScreen(
    isTransfer   : Boolean = false,
    orderId      : String = "#SCNT-99283",
    estimasi     : String = "2-3 Hari Kerja",
    onBackHome   : () -> Unit = {}
) {
    val repository = CartRepository.getInstance()
    val checkoutSummary by repository.checkoutSummary.collectAsState(initial = com.contoh.scentapp.data.repository.CheckoutSummary())
    val totalPayment = "Rp${"%,d".format(checkoutSummary.total).replace(',', '.')}"
    LaunchedEffect(Unit) {
        repository.clearCart()
    }

    val instruksiText = if (isTransfer) {
        stringResource(R.string.order_success_transfer_desc)
    } else {
        stringResource(R.string.order_success_desc_cod)
    }

    val metodeText = if (isTransfer) stringResource(R.string.order_success_transfer) else stringResource(R.string.order_success_cod)
    val metodeIcon = if (isTransfer) Icons.Default.AccountBalance else Icons.Default.Money

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp).clickable(onClick = onBackHome).align(Alignment.CenterStart)
                )
                Text(
                    text = "SCENT",
                    style = MaterialTheme.typography.titleLarge.copy(
                        letterSpacing = 6.sp, fontSize = 18.sp, fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier.size(120.dp).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.ok),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(28.dp))
            Text(
                text = stringResource(R.string.order_success_title),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 36.sp
                ),
                color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = instruksiText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f), lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surface).padding(20.dp)
            ) {
                OrderDetailRow(label = stringResource(R.string.order_id), value = orderId, isLabelSmall = true)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 16.dp))
                Column {
                    Text(
                        text = "METODE PEMBAYARAN",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = metodeIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = metodeText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 16.dp))
                Column {
                    Text(
                        text = stringResource(R.string.order_estimate),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp, letterSpacing = 1.5.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = estimasi,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.order_success_total_payment),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    )
                    Text(
                        text = totalPayment,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp, color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(horizontal = 20.dp, vertical = 16.dp).navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.onBackground).clickable(onClick = onBackHome).padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.order_success_back_home),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 12.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}

@Composable
private fun OrderDetailRow(label: String, value: String, isLabelSmall: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isLabelSmall) MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 1.5.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
            else MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f))
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        )
    }
}
