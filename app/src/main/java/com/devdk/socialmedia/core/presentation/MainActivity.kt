package com.devdk.socialmedia.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.devdk.socialmedia.core.presentation.ui.theme.SocialMediaTheme
import com.devdk.socialmedia.core.presentation.components.Navigation
import com.devdk.socialmedia.feature_chat.domain.use_cases.UserOfflineUseCase
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
