package com.contoh.scentapp.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.contoh.scentapp.ui.theme.*

@Composable
fun AccountDetailScreen(
    onBack: () -> Unit = {}
) {
    var name         by rememberSaveable { mutableStateOf("Julian Alexander") }
    var email        by rememberSaveable { mutableStateOf("julian.alex@atelier.com") }
    var password     by rememberSaveable { mutableStateOf("password123") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var address      by rememberSaveable { mutableStateOf("") }
    var photoUri     by rememberSaveable { mutableStateOf<Uri?>(null) }
    var showPhotoDialog by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val context   = LocalContext.current

    // ── Photo picker launchers ────────────────────────────────────────────────
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { photoUri = it } }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // For simplicity: store bitmap as a content URI via MediaStore
        // In production, save bitmap to cache and get URI
        // Here we leave photoUri unchanged if bitmap is null
    }

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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint               = ScentWhite,
                        modifier           = Modifier
                            .size(24.dp)
                            .clickable(onClick = onBack)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text  = "Detail Akun",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize   = 20.sp
                        ),
                        color = ScentWhite
                    )
                }
            }

            // ── Avatar ────────────────────────────────────────────────────────
            item(key = "avatar") {
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(ScentSearchBg)
                                .clickable { showPhotoDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (photoUri != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(photoUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Foto profil",
                                    contentScale       = ContentScale.Crop,
                                    modifier           = Modifier.fillMaxSize()
                                )
                            } else {
                                Icon(
                                    imageVector        = Icons.Default.Person,
                                    contentDescription = null,
                                    tint               = ScentTextMuted,
                                    modifier           = Modifier.size(56.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(ScentWhite)
                                .align(Alignment.BottomEnd)
                                .clickable { showPhotoDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector        = Icons.Default.Edit,
                                contentDescription = "Ganti foto",
                                tint               = ScentBlack,
                                modifier           = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text  = "GANTI FOTO",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 2.sp,
                            color         = ScentTextMuted
                        )
                    )
                }
            }

            // ── Nama ──────────────────────────────────────────────────────────
            item(key = "nama") {
                AccountFormField(
                    label    = "NAMA LENGKAP",
                    value    = name,
                    onChange = { name = it },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                HorizontalDivider(
                    color     = ScentDivider,
                    thickness = 0.5.dp,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }

            // ── Email ─────────────────────────────────────────────────────────
            item(key = "email") {
                AccountFormField(
                    label    = "EMAIL",
                    value    = email,
                    onChange = { email = it },
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                HorizontalDivider(
                    color     = ScentDivider,
                    thickness = 0.5.dp,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }

            // ── Password ──────────────────────────────────────────────────────
            item(key = "password") {
                PasswordFormField(
                    label        = "PASSWORD",
                    value        = password,
                    onChange     = { password = it },
                    showPassword = showPassword,
                    onToggle     = { showPassword = !showPassword },
                    modifier     = Modifier.padding(horizontal = 20.dp)
                )
                HorizontalDivider(
                    color     = ScentDivider,
                    thickness = 0.5.dp,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }

            // ── Alamat ────────────────────────────────────────────────────────
            item(key = "alamat") {
                AddressFormField(
                    value    = address,
                    onChange = { address = it },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                HorizontalDivider(
                    color     = ScentDivider,
                    thickness = 0.5.dp,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }

            // ── Security note ─────────────────────────────────────────────────
            item(key = "security") {
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF111111))
                        .border(0.5.dp, ScentDivider, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector        = Icons.Default.Security,
                        contentDescription = null,
                        tint               = ScentTextMuted,
                        modifier           = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text  = "Keamanan Akun",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ScentWhite
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text  = "Informasi pribadi Anda dienkripsi dengan standar industri atelier yang ketat.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color      = ScentTextMuted,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        // ── Tombol Simpan ─────────────────────────────────────────────────────
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
                    .clickable { onBack() }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "SIMPAN PERUBAHAN",
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

    // ── Dialog Pilih Foto ─────────────────────────────────────────────────────
    if (showPhotoDialog) {
        AlertDialog(
            onDismissRequest = { showPhotoDialog = false },
            containerColor   = Color(0xFF1C1C1C),
            title = {
                Text(
                    "Ganti Foto Profil",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ScentWhite
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Opsi Galeri
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF2A2A2A))
                            .clickable {
                                showPhotoDialog = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint     = ScentGold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Pilih dari Galeri",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentWhite)
                        )
                    }
                    // Opsi Kamera
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF2A2A2A))
                            .clickable {
                                showPhotoDialog = false
                                cameraLauncher.launch(null)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint     = ScentGold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Ambil Foto",
                            style = MaterialTheme.typography.bodyMedium.copy(color = ScentWhite)
                        )
                    }
                    // Hapus foto (hanya jika sudah ada foto)
                    if (photoUri != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF2A2A2A))
                                .clickable {
                                    photoUri = null
                                    showPhotoDialog = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.DeleteOutline,
                                contentDescription = null,
                                tint     = Color(0xFFCF6679),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Hapus Foto",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFCF6679))
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showPhotoDialog = false }) {
                    Text(
                        "BATAL",
                        color = ScentTextMuted,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                        )
                    )
                }
            }
        )
    }
}

// ── Form Fields ───────────────────────────────────────────────────────────────

@Composable
private fun AccountFormField(
    label        : String,
    value        : String,
    onChange     : (String) -> Unit,
    keyboardType : KeyboardType = KeyboardType.Text,
    modifier     : Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize      = 10.sp,
                letterSpacing = 1.5.sp,
                color         = ScentTextLabel
            )
        )
        Spacer(Modifier.height(8.dp))
        BasicTextField(
            value         = value,
            onValueChange = onChange,
            textStyle     = MaterialTheme.typography.titleMedium.copy(
                color      = ScentWhite,
                fontSize   = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            cursorBrush     = SolidColor(ScentGold),
            modifier        = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PasswordFormField(
    label        : String,
    value        : String,
    onChange     : (String) -> Unit,
    showPassword : Boolean,
    onToggle     : () -> Unit,
    modifier     : Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize      = 10.sp,
                letterSpacing = 1.5.sp,
                color         = ScentTextLabel
            )
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value                = value,
                onValueChange        = onChange,
                textStyle            = MaterialTheme.typography.titleMedium.copy(
                    color      = ScentWhite,
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
                cursorBrush          = SolidColor(ScentGold),
                modifier             = Modifier.weight(1f)
            )
            Icon(
                imageVector        = if (showPassword) Icons.Default.VisibilityOff
                else Icons.Default.Visibility,
                contentDescription = "Toggle password",
                tint               = ScentTextMuted,
                modifier           = Modifier
                    .size(22.dp)
                    .clickable(onClick = onToggle)
            )
        }
    }
}

@Composable
private fun AddressFormField(
    value    : String,
    onChange : (String) -> Unit,
    modifier : Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector        = Icons.Default.LocationOn,
                contentDescription = null,
                tint               = ScentTextLabel,
                modifier           = Modifier.size(12.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text  = "ALAMAT PENGIRIMAN",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize      = 10.sp,
                    letterSpacing = 1.5.sp,
                    color         = ScentTextLabel
                )
            )
        }
        Spacer(Modifier.height(8.dp))
        BasicTextField(
            value         = value,
            onValueChange = onChange,
            textStyle     = MaterialTheme.typography.titleMedium.copy(
                color      = ScentWhite,
                fontSize   = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            ),
            cursorBrush   = SolidColor(ScentGold),
            maxLines      = 4,
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text  = "Masukkan alamat lengkap, kelurahan, kota...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color      = ScentTextMuted.copy(alpha = 0.5f),
                                fontSize   = 16.sp,
                                lineHeight = 24.sp
                            )
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}