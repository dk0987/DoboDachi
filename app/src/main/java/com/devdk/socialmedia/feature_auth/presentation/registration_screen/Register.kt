package com.devdk.socialmedia.feature_auth.presentation.registration_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardButtons
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.background
import com.devdk.socialmedia.core.presentation.ui.theme.onBackground
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes

@Composable
fun Register(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val usernameState = registerViewModel.usernameTextFieldState.value
    val passwordState = registerViewModel.passwordTextFieldState.value
    val eMailState = registerViewModel.eMailTextFieldState.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = stringResource(id = R.string.register),
                fontWeight = FontWeight.SemiBold,
                fontSize = 35.sp ,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = primaryText
            )
            Spacer(modifier = Modifier.height(5.dp))
            StandardTextField(
                placeholder = stringResource(id = R.string.username),
                text = usernameState.text,
                keyboardType = KeyboardType.Text,
                onValueChange = {registerViewModel.onEvent(RegisterEvents.Username(it))},
                isError = usernameState.isError,
                error = usernameState.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            StandardTextField(
                placeholder = stringResource(id = R.string.eMail),
                text = eMailState.text,
                keyboardType = KeyboardType.Email,
                onValueChange = {registerViewModel.onEvent(RegisterEvents.Email(it))},
                isError = eMailState.isError,
                error = eMailState.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            StandardTextField(
                placeholder = stringResource(id = R.string.password),
                text = passwordState.text,
                onValueChange = {registerViewModel.onEvent(RegisterEvents.Password(it))},
                keyboardType = KeyboardType.Password ,
                passwordVisible = passwordState.isPasswordVisible,
                togglePassword = {registerViewModel.onEvent(RegisterEvents.Toggle)},
                isError = passwordState.isError,
                error = passwordState.error
            )
            Spacer(modifier = Modifier.height(10.dp))
            StandardButtons(
                text = stringResource(id = R.string.sign_up),
                onClick = { registerViewModel.onEvent(RegisterEvents.Register) },
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.have_an_account)+ " ",
                color = primaryText,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.sign_in),
                color = onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                        navController.navigate(Routes.Login.screen)
                    }
            )
        }
    }
}