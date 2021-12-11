package com.devdk.socialmedia.feature_profile.presentation.profile_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devdk.socialmedia.R

@ExperimentalMaterialApi
@Composable
fun ProfileInfo(
    following : Int,
    onFollowing : () -> Unit,
    followers : Int ,
    onFollowers : () -> Unit,
    post :Int,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoBox(text = stringResource(id = R.string.following), number = following , onClick = onFollowing)
        InfoBox(text = stringResource(id = R.string.followers), number = followers , onClick = onFollowers)
        InfoBox(text = stringResource(id = R.string.posts), number = post )
    }
}