package com.contoh.scent.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {} // Untuk tombol <- MASUK
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Definisi Warna Kustom Sesuai Desain
    val bgColor = Color(0xFF121212) // Hitam gelap
    val textColor = Color.White
    val subTextColor = Color(0xFFAAAAAA) // Abu-abu terang
    val inputBgColor = Color(0xFF1A1A1A) // Agak terang dari background
    val borderColor = Color(0xFF333333)

    LaunchedEffect(state.errorMessage, state.isSuccess) {
        if (state.errorMessage != null) {
            snackbarHostState.showSnackbar(state.errorMessage!!)
            viewModel.clearError()
        }
        if (state.isSuccess) {
            onNavigateToHome()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = bgColor // Set background scaffold
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // --- TOP BAR KUSTOM ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SCENT",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
                Text(
                    text = "← MASUK",
                    color = subTextColor,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- HEADER TEKS ---
            Text(
                text = "Buat Akun",
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Masukkan detail Anda untuk bergabung\ndengan atelier digital kami dan jelajahi koleksi\neksklusif.",
                color = subTextColor,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // --- FORM NAMA LENGKAP ---
            Text(
                text = "NAMA LENGKAP",
                color = subTextColor,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.nameInput,
                onValueChange = viewModel::onNameChange,
                placeholder = { Text("ALEXANDER VOGUE", color = borderColor) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = inputBgColor,
                    unfocusedContainerColor = inputBgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- FORM EMAIL ---
            Text(
                text = "ALAMAT EMAIL",
                color = subTextColor,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.emailInput,
                onValueChange = viewModel::onEmailChange,
                placeholder = { Text("EMAIL@ATELIER.COM", color = borderColor) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = inputBgColor,
                    unfocusedContainerColor = inputBgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- FORM KATA SANDI ---
            Text(
                text = "KATA SANDI",
                color = subTextColor,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.passwordInput,
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text("••••••••", color = borderColor) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        text = if (state.isPasswordVisible) "TUTUP" else "LIHAT",
                        color = subTextColor,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { viewModel.togglePasswordVisibility() }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = inputBgColor,
                    unfocusedContainerColor = inputBgColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- TOMBOL DAFTAR ---
            Button(
                onClick = { viewModel.registerUser() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    disabledContainerColor = Color.Gray
                ),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = bgColor,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "DAFTAR",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen()
}