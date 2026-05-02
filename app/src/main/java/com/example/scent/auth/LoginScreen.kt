            package com.example.scent.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    val state by viewModel.state

    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFFA0A0A0)
    val inputLineColor = Color(0xFF333333)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
        )

        Text(
            text = stringResource(id = R.string.welcome_back),
            color = textColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.login_desc),
            color = secondaryTextColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Text(
            text = stringResource(id = R.string.email_label),
            color = secondaryTextColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )

        TextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = {
                Text(text = stringResource(id = R.string.email_placeholder), color = Color(0xFF4A4A4A))
            },
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
                .padding(bottom = 24.dp)
        )

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
                modifier = Modifier.clickable { }
            )
        }

        TextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            visualTransformation = PasswordVisualTransformation(),
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

        Button(
            onClick = { viewModel.login() },
            enabled = !state.isLoading,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = textColor,
                contentColor = backgroundColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = backgroundColor,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(id = R.string.btn_login),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

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
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}