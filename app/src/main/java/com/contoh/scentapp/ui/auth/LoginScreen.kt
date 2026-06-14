package com.contoh.scentapp.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import com.contoh.scentapp.R
import com.contoh.scentapp.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess : () -> Unit = {},
    onRegister     : () -> Unit = {},
    viewModel      : AuthViewModel = viewModel(
        factory = com.contoh.scentapp.di.ViewModelFactory.authFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var email    by rememberSaveable { mutableStateOf(uiState.loginEmail) }
    var password by rememberSaveable { mutableStateOf(uiState.loginPassword) }
    var showForgotDialog by remember { mutableStateOf(false) }
    var forgotEmail      by remember { mutableStateOf("") }
    var forgotSent       by remember { mutableStateOf(false) }

    LaunchedEffect(email)    { viewModel.onLoginEmailChange(email) }
    LaunchedEffect(password) { viewModel.onLoginPasswordChange(password) }

    val bg      = MaterialTheme.colorScheme.background
    val onBg    = MaterialTheme.colorScheme.onBackground
    val muted   = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    val label   = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
    val divider = MaterialTheme.colorScheme.outline
    val btnBg   = MaterialTheme.colorScheme.onBackground
    val btnText = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(48.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text  = "SCENT",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight    = FontWeight.Bold,
                        letterSpacing = 6.sp,
                        fontSize      = 20.sp
                    ),
                    color    = onBg,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(Modifier.height(40.dp))
            Text(
                text  = stringResource(R.string.login_welcome_back),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize   = 36.sp,
                    lineHeight = 44.sp
                ),
                color = onBg
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text  = stringResource(R.string.login_subtitle),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color      = muted,
                    lineHeight = 22.sp
                )
            )
            Spacer(Modifier.height(40.dp))
            Text(
                text  = stringResource(R.string.auth_email),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize      = 10.sp,
                    letterSpacing = 2.sp,
                    color         = label
                )
            )
            Spacer(Modifier.height(10.dp))
            BasicTextField(
                value           = email,
                onValueChange   = { email = it },
                textStyle       = MaterialTheme.typography.bodyMedium.copy(
                    color    = onBg,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                cursorBrush     = SolidColor(ScentGold),
                singleLine      = true,
                modifier        = Modifier.fillMaxWidth(),
                decorationBox   = { inner ->
                    if (email.isEmpty()) {
                        Text(
                            text  = "atelier@scent.com",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = muted, fontSize = 16.sp
                            )
                        )
                    }
                    inner()
                }
            )
            HorizontalDivider(
                color     = divider,
                thickness = 0.5.dp,
                modifier  = Modifier.padding(top = 10.dp)
            )
            Spacer(Modifier.height(28.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text  = stringResource(R.string.auth_password),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize      = 10.sp,
                        letterSpacing = 2.sp,
                        color         = label
                    )
                )
                Text(
                    text  = stringResource(R.string.login_forgot_password),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize      = 10.sp,
                        letterSpacing = 1.5.sp,
                        color         = muted
                    ),
                    modifier = Modifier.clickable { showForgotDialog = true }
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value                = password,
                    onValueChange        = { password = it },
                    textStyle            = MaterialTheme.typography.bodyMedium.copy(
                        color = onBg, fontSize = 16.sp
                    ),
                    visualTransformation = if (uiState.showLoginPass)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
                    cursorBrush          = SolidColor(ScentGold),
                    singleLine           = true,
                    modifier             = Modifier.weight(1f),
                    decorationBox        = { inner ->
                        if (password.isEmpty()) {
                            Text(
                                text  = "â€¢â€¢â€¢â€¢â€¢â€¢â€¢â€¢",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = muted, fontSize = 16.sp
                                )
                            )
                        }
                        inner()
                    }
                )
                Icon(
                    imageVector        = if (uiState.showLoginPass) Icons.Default.VisibilityOff
                    else Icons.Default.Visibility,
                    contentDescription = "Toggle password",
                    tint               = muted,
                    modifier           = Modifier
                        .size(20.dp)
                        .clickable { viewModel.toggleLoginPasswordVisibility() }
                )
            }
            HorizontalDivider(
                color     = divider,
                thickness = 0.5.dp,
                modifier  = Modifier.padding(top = 10.dp)
            )
            uiState.errorMessage?.let { error ->
                Spacer(Modifier.height(12.dp))
                Text(
                    text  = error,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(btnBg)
                    .clickable(enabled = !uiState.isLoading) {
                        viewModel.login(onSuccess = onLoginSuccess)
                    }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color       = btnText,
                        modifier    = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text  = stringResource(R.string.login_button),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 12.sp,
                            letterSpacing = 3.sp,
                            fontWeight    = FontWeight.Bold,
                            color         = btnText
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text  = stringResource(R.string.login_new_user),
                style = MaterialTheme.typography.bodyMedium.copy(color = muted)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text  = stringResource(R.string.login_register_now),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color      = onBg
                ),
                modifier = Modifier.clickable(onClick = onRegister)
            )
        }
    }

    if (showForgotDialog) {
        AlertDialog(
            onDismissRequest = { showForgotDialog = false; forgotSent = false },
            containerColor   = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                    text  = "Reset Password",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            text = {
                Column {
                    if (forgotSent) {
                        Text(
                            text  = "Email reset password telah dikirim ke $forgotEmail. Cek inbox Anda.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color      = MaterialTheme.colorScheme.onBackground,
                                lineHeight = 22.sp
                            )
                        )
                    } else {
                        Text(
                            text  = "Masukkan email akun Anda untuk menerima link reset password.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                            )
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value           = forgotEmail,
                            onValueChange   = { forgotEmail = it },
                            label           = { Text("Email") },
                            singleLine      = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier        = Modifier.fillMaxWidth(),
                            colors          = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                    }
                }
            },
            confirmButton = {
                if (!forgotSent) {
                    TextButton(
                        onClick = {
                            if (forgotEmail.isNotBlank()) {
                                com.google.firebase.auth.FirebaseAuth.getInstance()
                                    .sendPasswordResetEmail(forgotEmail)
                                forgotSent = true
                            }
                        }
                    ) {
                        Text(
                            text  = "KIRIM",
                            color = ScentGold,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                            )
                        )
                    }
                } else {
                    TextButton(onClick = { showForgotDialog = false; forgotSent = false }) {
                        Text(
                            text  = "OK",
                            color = ScentGold,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                            )
                        )
                    }
                }
            },
            dismissButton = {
                if (!forgotSent) {
                    TextButton(onClick = { showForgotDialog = false }) {
                        Text(
                            text  = "Batal",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                            )
                        )
                    }
                }
            }
        )
    }
}
