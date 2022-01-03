package com.devdk.socialmedia.feature_chat.presentation.chats_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.feature_chat.domain.modal.ChatFollowings

@ExperimentalMaterialApi
@Composable
fun ChatFollowerList(
    chatFollowings: ChatFollowings ,
    elevation : Dp = 10.dp,
    onClick : () -> Unit = {},
    size : Dp = 50.dp,
) {
    Column(
        Modifier.padding(horizontal = 3.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier.fillMaxWidth() ,
            contentAlignment = Alignment.CenterStart
        ){
            Card(
                onClick = onClick,
                shape = CircleShape,
                elevation = elevation,
            ) {
                Image(
                    painter = rememberImagePainter(data = chatFollowings.userProfileUrl , builder = {
                        crossfade(true)
                        size(OriginalSize)
                    }),
                    contentDescription = stringResource(id = R.string.profile_pic),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }
            if (chatFollowings.isOnline){
                Box(modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .align(Alignment.BottomEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = chatFollowings.username,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = primaryText,
            letterSpacing = 0.9.sp
        )
    }


}