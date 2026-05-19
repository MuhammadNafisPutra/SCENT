package com.contoh.scentapp.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contoh.scentapp.ui.theme.ScentBlack
import com.contoh.scentapp.ui.theme.ScentGold
import com.contoh.scentapp.ui.theme.ScentTextMuted

@Composable
fun FavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScentBlack)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector        = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint               = ScentGold,
                modifier           = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text  = "Favorit",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = "Belum ada parfum favorit",
                style = MaterialTheme.typography.bodyMedium.copy(color = ScentTextMuted)
            )
        }
    }
}