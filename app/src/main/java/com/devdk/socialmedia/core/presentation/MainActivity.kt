package com.devdk.socialmedia.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.devdk.socialmedia.core.presentation.ui.theme.SocialMediaTheme
import com.devdk.socialmedia.core.presentation.util.Navigation
import com.devdk.socialmedia.feature_auth.presentation.login_screen.Login
import com.devdk.socialmedia.feature_auth.presentation.registration_screen.Register
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOfflineUseCase
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOnlineUseCase
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var offlineUseCase: UserOfflineUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaTheme {
                Navigation()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        GlobalScope.launch {
            offlineUseCase()
        }
    }
}
