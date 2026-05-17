package com.example.week2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week2.ui.theme.Week2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SejarahAndroidScreen()
                }
            }
        }
    }
}

@Composable
fun SejarahAndroidScreen() {
    val daftarSejarah = listOf(
        "Tahun 2003, Android didirikan oleh Andy Rubin bersama Rich Miner, Nick Sears, dan Chris White. Pada awalnya sistem ini dikembangkan untuk kamera digital, namun kemudian dialihkan untuk perangkat smartphone.",
        "Tahun 2005, Google resmi mengakuisisi Android Inc. dan mulai mengembangkan Android secara lebih luas sebagai bagian dari perusahaannya.",
        "Tahun 2007, Android diperkenalkan ke publik bersamaan dengan dibentuknya Open Handset Alliance (OHA) yang mendukung pengembangan standar terbuka untuk perangkat seluler.",
        "Tahun 2008, smartphone Android pertama yaitu HTC Dream (T-Mobile G1) dirilis secara komersial pada bulan Oktober.",
        "Saat ini, Android telah berkembang pesat dan menjadi sistem operasi seluler paling populer di dunia dengan miliaran pengguna aktif."
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Android",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF3DDC84),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 4.dp)
        )

        Text(
            text = "Sejarah",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        daftarSejarah.forEach { item ->
            Text(
                text = item,
                fontSize = 16.sp,
                color = Color.DarkGray,
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
    }
}