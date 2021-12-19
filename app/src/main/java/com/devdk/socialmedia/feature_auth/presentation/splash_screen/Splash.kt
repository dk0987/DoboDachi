package com.devdk.socialmedia.feature_auth.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.core.presentation.ui.theme.background
import com.devdk.socialmedia.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Splash(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true ){
        splashViewModel.eventFlow.collectLatest {
            when(it){
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(it.route)
                }
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(background))
}