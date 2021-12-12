package com.devdk.socialmedia.feature_search.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.background
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.followButton
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@ExperimentalMaterialApi
@Composable
fun StandardFollowButton(
    isFollowing : Boolean ,
    modifier: Modifier = Modifier ,
    OnClick : () -> Unit,
) {
    Card(
        onClick = OnClick ,
        elevation = 10.dp ,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.size(80.dp , 35.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isFollowing) Color.Transparent else followButton)
                .border(BorderStroke(1.5.dp, if (isFollowing) followButton else Color.White) , shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = if (isFollowing) stringResource(id = R.string.unfollow) else stringResource(id = R.string.follow),
                color = if (isFollowing) primaryText else background ,
                fontSize = 11.5.sp ,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

    }
}