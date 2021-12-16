package com.devdk.socialmedia.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.devdk.socialmedia.core.presentation.ui.theme.SocialMediaTheme
import com.devdk.socialmedia.core.presentation.util.Navigation
import com.devdk.socialmedia.feature_auth.presentation.login_screen.Login
import com.devdk.socialmedia.feature_auth.presentation.registration_screen.Register
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaTheme {

                Navigation()
            }
        }
    }
}
