package com.contoh.scentapp.ui.profile

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.scentapp.data.remote.dto.CityDto
import com.contoh.scentapp.data.remote.dto.ProvinceDto
import com.contoh.scentapp.ui.theme.*
import androidx.compose.ui.res.stringResource
import com.contoh.scentapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingAddressScreen(
    onBack : () -> Unit = {},
    viewModel: ShippingAddressViewModel = viewModel()
) {
    var namaPenerima   by rememberSaveable { mutableStateOf("") }
    var noTelepon      by rememberSaveable { mutableStateOf("") }
    var kodePos        by rememberSaveable { mutableStateOf("") }
    var alamatLengkap  by rememberSaveable { mutableStateOf("") }
    var labelAlamat    by rememberSaveable { mutableStateOf("RUMAH") }
    var isAlamatUtama  by rememberSaveable { mutableStateOf(false) }
    
    val provinces by viewModel.provinces.collectAsState()
    val cities by viewModel.cities.collectAsState()
    val savedAddressObj by viewModel.savedAddressObj.collectAsState()
    
    var selectedProvince by remember { mutableStateOf<ProvinceDto?>(null) }
    var selectedCity by remember { mutableStateOf<CityDto?>(null) }

    LaunchedEffect(savedAddressObj) {
        savedAddressObj?.let { obj ->
            namaPenerima = obj["nama"] as? String ?: ""
            noTelepon = obj["telepon"] as? String ?: ""
            alamatLengkap = obj["alamat"] as? String ?: ""
            kodePos = obj["kodePos"] as? String ?: ""
            labelAlamat = obj["label"] as? String ?: "RUMAH"
            isAlamatUtama = obj["isUtama"] as? Boolean ?: false
            
            val pId = obj["provId"] as? String
            val pName = obj["provName"] as? String
            val cId = obj["cityId"] as? String
            val cName = obj["cityName"] as? String
            
            if (pId != null && pName != null) {
                selectedProvince = ProvinceDto(pId, pName)
                viewModel.fetchCities(pId)
            }
            if (cId != null && cName != null) {
                selectedCity = CityDto(cId, pId ?: "", cName)
            }
        }
    }
    
    var expandedProvince by remember { mutableStateOf(false) }
    var expandedCity by remember { mutableStateOf(false) }

    val listState       = rememberLazyListState()
    val context = androidx.compose.ui.platform.LocalContext.current
    val labelOptions = listOf(
        context.getString(R.string.shipping_address_label_home), 
        context.getString(R.string.shipping_address_label_office), 
        context.getString(R.string.shipping_address_label_other)
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
        LazyColumn(
            state          = listState,
            modifier       = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
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
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier           = Modifier
                            .size(24.dp)
                            .clickable(onClick = onBack)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text  = stringResource(R.string.shipping_address_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight    = FontWeight.Bold,
                            fontSize      = 14.sp,
                            letterSpacing = 2.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            item(key = "header") {
                Column(
                    modifier = Modifier.padding(
                        start  = 20.dp,
                        end    = 20.dp,
                        top    = 8.dp,
                        bottom = 28.dp
                    )
                ) {
                    Text(
                        text  = stringResource(R.string.shipping_address_info_title),
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize   = 26.sp,
                            lineHeight = 32.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text  = stringResource(R.string.shipping_address_info_desc),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            lineHeight = 22.sp
                        )
                    )
                }
            }
            item(key = "nama") {
                AddressFormField(
                    label       = stringResource(R.string.shipping_address_recipient_name),
                    value       = namaPenerima,
                    onChange    = { namaPenerima = it },
                    placeholder = stringResource(R.string.shipping_address_recipient_name_hint),
                    modifier    = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
            item(key = "telepon") {
                AddressFormField(
                    label           = stringResource(R.string.shipping_address_phone),
                    value           = noTelepon,
                    onChange        = { noTelepon = it },
                    placeholder     = stringResource(R.string.shipping_address_phone_hint),
                    keyboardType    = KeyboardType.Phone,
                    modifier        = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
            
            // PROVINCE DROPDOWN
            item(key = "provinsi") {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        text  = stringResource(R.string.shipping_address_province),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedProvince,
                        onExpandedChange = { expandedProvince = !expandedProvince }
                    ) {
                        OutlinedTextField(
                            value = selectedProvince?.name ?: stringResource(R.string.shipping_address_province_hint),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProvince) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedProvince,
                            onDismissRequest = { expandedProvince = false }
                        ) {
                            provinces.forEach { province ->
                                DropdownMenuItem(
                                    text = { Text(province.name) },
                                    onClick = {
                                        selectedProvince = province
                                        expandedProvince = false
                                        selectedCity = null
                                        viewModel.fetchCities(province.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // CITY AND POSTAL CODE
            item(key = "kota_kodepos") {
                Row(
                    modifier              = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1.5f)) {
                        Text(
                            text  = stringResource(R.string.shipping_address_city),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize      = 10.sp,
                                letterSpacing = 1.5.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        )
                        Spacer(Modifier.height(10.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedCity,
                            onExpandedChange = { if(cities.isNotEmpty()) expandedCity = !expandedCity }
                        ) {
                            OutlinedTextField(
                                value = selectedCity?.name ?: stringResource(R.string.shipping_address_city_hint),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCity) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = expandedCity,
                                onDismissRequest = { expandedCity = false }
                            ) {
                                cities.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city.name) },
                                        onClick = {
                                            selectedCity = city
                                            expandedCity = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    AddressFormField(
                        label       = stringResource(R.string.shipping_address_zip),
                        value       = kodePos,
                        onChange    = { kodePos = it },
                        placeholder = stringResource(R.string.shipping_address_zip_hint),
                        keyboardType = KeyboardType.Number,
                        modifier    = Modifier.weight(1f)
                    )
                }
            }

            item(key = "alamat") {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text  = stringResource(R.string.shipping_address_full),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                            .padding(14.dp)
                    ) {
                        BasicTextField(
                            value         = alamatLengkap,
                            onValueChange = { alamatLengkap = it },
                            textStyle     = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            cursorBrush   = SolidColor(ScentGold),
                            minLines      = 3,
                            modifier      = Modifier.fillMaxWidth(),
                            decorationBox = { inner ->
                                if (alamatLengkap.isEmpty()) {
                                    Text(
                                        text  = stringResource(R.string.shipping_address_full_hint),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                                            lineHeight = 22.sp
                                        )
                                    )
                                }
                                inner()
                            }
                        )
                    }
                }
            }
            item(key = "label") {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text  = stringResource(R.string.shipping_address_label),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize      = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        labelOptions.forEach { option ->
                            val isSelected = option == labelAlamat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Transparent
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp)
                                    )
                                    .clickable { labelAlamat = option }
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text  = option,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize      = 10.sp,
                                        letterSpacing = 1.5.sp,
                                        fontWeight    = FontWeight.Bold,
                                        color         = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            item(key = "utama") {
                Row(
                    modifier          = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .clickable { isAlamatUtama = !isAlamatUtama },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked         = isAlamatUtama,
                        onCheckedChange = { isAlamatUtama = it },
                        colors          = CheckboxDefaults.colors(
                            checkedColor        = MaterialTheme.colorScheme.onBackground,
                            uncheckedColor      = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                            checkmarkColor      = MaterialTheme.colorScheme.background
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text  = stringResource(R.string.shipping_address_set_main),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .clickable { 
                        if (namaPenerima.isBlank() || noTelepon.isBlank() || selectedCity == null || selectedProvince == null || alamatLengkap.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(context.getString(R.string.shipping_address_incomplete))
                            }
                        } else {
                            viewModel.saveDestinationCity(selectedCity!!.id)
                            viewModel.saveStructuredAddress(
                                nama = namaPenerima,
                                telepon = noTelepon,
                                alamat = alamatLengkap,
                                kodePos = kodePos,
                                provId = selectedProvince!!.id,
                                provName = selectedProvince!!.name,
                                cityId = selectedCity!!.id,
                                cityName = selectedCity!!.name,
                                label = labelAlamat,
                                isUtama = isAlamatUtama
                            )
                            onBack() 
                        }
                    }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = stringResource(R.string.shipping_address_save),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize      = 12.sp,
                        letterSpacing = 2.sp,
                        fontWeight    = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}
}

@Composable
private fun AddressFormField(
    label        : String,
    value        : String,
    onChange     : (String) -> Unit,
    placeholder  : String        = "",
    keyboardType : KeyboardType  = KeyboardType.Text,
    modifier     : Modifier      = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize      = 10.sp,
                letterSpacing = 1.5.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        )
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            BasicTextField(
                value           = value,
                onValueChange   = onChange,
                textStyle       = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                cursorBrush     = SolidColor(ScentGold),
                singleLine      = true,
                modifier        = Modifier.fillMaxWidth(),
                decorationBox   = { inner ->
                    if (value.isEmpty()) {
                        Text(
                            text  = placeholder,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                                fontSize = 16.sp
                            )
                        )
                    }
                    inner()
                }
            )
        }
    }
}