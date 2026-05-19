package com.example.scent.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {

    val bgColor = Color(0xFF121212)
    val textColor = Color.White
    val subTextColor = Color(0xFFA0A0A0)
    val dividerColor = Color(0xFF222222)

    var isDarkMode by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "AKUN",
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Keluar",
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2C2C2C)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF555555),
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = "Nama",
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "email",
                    color = subTextColor,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(0xFF444444),
                            RoundedCornerShape(4.dp)
                        )
                        .clickable { }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {

                    Text(
                        text = "EDIT PROFIL",
                        color = subTextColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "INFORMASI PRIBADI",
            color = subTextColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        )

        MenuItem(
            icon = Icons.Default.Person,
            title = "Detail Akun"
        )

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        MenuItem(
            icon = Icons.Default.LocationOn,
            title = "Alamat Pengiriman"
        )

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PREFERENSI APLIKASI",
            color = subTextColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "Bahasa",
                    color = textColor,
                    fontSize = 14.sp
                )

                Text(
                    text = "INDONESIA",
                    color = subTextColor,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = subTextColor
            )
        }

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Mode Gelap",
                color = textColor,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF4CAF50)
                )
            )
        }

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        MenuItem(
            icon = Icons.Default.ShoppingCart,
            title = "Penjualan"
        )

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF444444),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable { }
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "HAPUS AKUN",
                color = subTextColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A0A0A))
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomNavItem(
                icon = Icons.Default.Home,
                title = "BERANDA",
                isActive = false
            )

            BottomNavItem(
                icon = Icons.Default.FavoriteBorder,
                title = "FAVORIT",
                isActive = false
            )

            BottomNavItem(
                icon = Icons.Default.ShoppingCart,
                title = "KERANJANG",
                isActive = false
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                title = "PROFIL",
                isActive = true
            )
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFA0A0A0)
        )
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    title: String,
    isActive: Boolean
) {

    val color =
        if (isActive) Color.White
        else Color(0xFF666666)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = color,
            fontSize = 9.sp,
            fontWeight =
                if (isActive) FontWeight.Bold
                else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=800dp")
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}