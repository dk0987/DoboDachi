package com.devdk.socialmedia.feature_auth.presentation.login_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.res.stringResource
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

@SuppressLint("PrivateResource")
@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val usernameState = loginViewModel.eMailTextFieldState.value
    val passwordState = loginViewModel.passwordTextFieldState.value
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
                text = stringResource(id = R.string.login),
                fontWeight = FontWeight.SemiBold,
                fontSize = 35.sp ,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = primaryText
            )
            Spacer(modifier = Modifier.height(5.dp))
            StandardTextField(
                placeholder = stringResource(id = R.string.eMail),
                text = usernameState.text,
                keyboardType = KeyboardType.Text,
                onValueChange = {loginViewModel.onEvent(LoginEvents.Email(it))},
                isError = usernameState.isError,
                error = usernameState.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            StandardTextField(
                placeholder = stringResource(id = R.string.password),
                text = passwordState.text,
                onValueChange = {loginViewModel.onEvent(LoginEvents.Password(it))},
                keyboardType = KeyboardType.Password ,
                passwordVisible = passwordState.isPasswordVisible,
                togglePassword = {loginViewModel.onEvent(LoginEvents.Toggle)},
                isError = passwordState.isError,
                error = passwordState.error
            )
            Spacer(modifier = Modifier.height(10.dp))
            StandardButtons(
                text = stringResource(id = R.string.sign_in),
                onClick = {
                    loginViewModel.onEvent(LoginEvents.Login)
                    navController.popBackStack()
                    navController.navigate(Routes.Feed.screen)
                },
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
                text = stringResource(id = R.string.dont_have_account) + " ",
                color = primaryText,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.sign_up),
                color = onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                        navController.navigate(Routes.Register.screen)
                    }
            )
        }
    }
}