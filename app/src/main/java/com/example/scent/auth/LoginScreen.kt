package com.example.scent.ui.screens.auth // Sesuaikan dengan package-mu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scent.R // Pastikan import ini sesuai dengan package aplikasi kamu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    // State untuk menyimpan input teks
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Warna kustom sesuai desain Dark Mode SCENT
    val backgroundColor = Color(0xFF121212) // Hitam gelap
    val textColor = Color.White
    val secondaryTextColor = Color(0xFFA0A0A0) // Abu-abu terang
    val inputLineColor = Color(0xFF333333) // Abu-abu gelap untuk garis bawah

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Logo / Header Top
        Text(
            text = stringResource(id = R.string.app_name),
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp, // Memberikan jarak antar huruf
            modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
        )

        // Judul
        Text(
            text = stringResource(id = R.string.welcome_back),
            color = textColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Sub-judul
        Text(
            text = stringResource(id = R.string.login_desc),
            color = secondaryTextColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Kolom Input: Alamat Email
        Text(
            text = stringResource(id = R.string.email_label),
            color = secondaryTextColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(text = stringResource(id = R.string.email_placeholder), color = Color(0xFF4A4A4A))
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = textColor, // Garis putih saat diklik
                unfocusedIndicatorColor = inputLineColor // Garis abu-abu saat diam
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Kolom Input: Kata Sandi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.password_label),
                color = secondaryTextColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = secondaryTextColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { /* TODO: Aksi Lupa Sandi */ }
            )
        }
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(), // Menyamarkan teks (titik-titik)
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = textColor,
                unfocusedIndicatorColor = inputLineColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )

        // Tombol MASUK
        Button(
            onClick = { /* TODO: Aksi Login ke Firebase */ },
            shape = RoundedCornerShape(4.dp), // Sedikit membulat di ujung
            colors = ButtonDefaults.buttonColors(
                containerColor = textColor, // Latar tombol putih
                contentColor = backgroundColor // Teks tombol hitam
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_login),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Teks Daftar Sekarang di bagian bawah
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = secondaryTextColor)) {
                    append(stringResource(id = R.string.new_to_scent))
                }
                withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.register_now))
                }
            }
            Text(
                text = annotatedText,
                fontSize = 12.sp,
                modifier = Modifier.clickable { /* TODO: Navigasi ke Layar Daftar */ }
            )
        }
    }
}

// Fungsi ini agar kamu bisa melihat Preview desainnya langsung di Android Studio
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}