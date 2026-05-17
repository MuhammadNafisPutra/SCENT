package com.example.fiturmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }
            AppTheme(useDarkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeightCalculatorScreen(
                        isDarkMode = isDarkMode,
                        onThemeChange = { isDarkMode = it }
                    )
                }
            }
        }
    }
}

@Composable
fun AppTheme(useDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (useDarkTheme) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colors, content = content)
}

@Composable
fun WeightCalculatorScreen(isDarkMode: Boolean, onThemeChange: (Boolean) -> Unit) {
    var weightInput by rememberSaveable {
        mutableStateOf("")
    }
    var heightInput by rememberSaveable {
        mutableStateOf("")
    }
    var gender by rememberSaveable {
        mutableStateOf("Laki-laki")
    }

    val weight = weightInput.toDoubleOrNull() ?: 0.0

    val status = if (weight == 0.0) {
        "Masukkan berat badan"
    } else if (gender == "Laki-laki") {
        when {
            weight < 60 -> "Kurus"
            weight <= 85 -> "Normal"
            else -> "Gemuk"
        }
    } else {
        when {
            weight < 45 -> "Kurus"
            weight <= 70 -> "Normal"
            else -> "Gemuk"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Kalkulator Berat Badan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Text("Jenis Kelamin:", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (gender == "Laki-laki"),
                        onClick = { gender = "Laki-laki" }
                    )
                    Text(
                        text = "Laki-laki",
                        modifier = Modifier.padding(end = 24.dp)
                    )
                    RadioButton(
                        selected = (gender == "Perempuan"),
                        onClick = { gender = "Perempuan" }
                    )
                    Text(text = "Perempuan")
                }
            }
            TextField(
                value = heightInput,
                onValueChange = { heightInput = it },
                label = { Text("Tinggi Badan (cm)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            TextField(
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text("Berat Badan (kg)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dark Mode", fontWeight = FontWeight.SemiBold)
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onThemeChange
                )
            }
            Spacer(modifier = Modifier.height(4.dp)
            )
            Text(
                text = "Status Anda:",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = status,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = if (status == "Normal") MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}