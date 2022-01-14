package com.devdk.socialmedia.feature_activity.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.feature_activity.domain.modal.Activity
import com.devdk.socialmedia.feature_activity.presentation.util.Action
import com.devdk.socialmedia.feature_activity.presentation.util.COMMENT
import com.devdk.socialmedia.feature_activity.presentation.util.POST

@ExperimentalMaterialApi
@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    activity: Activity ,
    elevation : Dp = 10.dp,
    roundedCornerShape : Dp = 15.dp,
    onProfilePic : () -> Unit = {},
    profile_pic_size : Dp = 35.dp,
    OnClick : () -> Unit = {},
    onPostText : () -> Unit = {} ,
    onCommentText : () -> Unit = {},
) {
    Card(
        onClick = OnClick ,
        elevation = elevation ,
        shape = RoundedCornerShape(roundedCornerShape),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        backgroundColor = container
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onProfilePic,
                Modifier
                    .clip(CircleShape)
                    .size(profile_pic_size)
            ) {
                Image(
                    painter = rememberImagePainter(data = activity.userProfileUrl, builder = {
                        crossfade(true)
                        size(OriginalSize)
                    }),
                    contentDescription = stringResource(id = R.string.profile_pic),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = activity.username,
                color = primaryText,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(
                        onClick = onProfilePic
                    )
            )
            when(val action = activity.action){
                is Action.Followed -> {
                    Text(
                        text = action.actionText ,
                        color = primaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
                is Action.Comment -> {
                    Text(
                        text = action.actionText.substringBefore(POST) ,
                        color = primaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Light,
                    )
                    Text(
                        text = POST,
                        color = primaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(
                            onClick = onCommentText
                        )
                    )
                }
                is Action.Liked -> {
                    when(action.likedOn){
                        POST -> {
                            Row {
                                Text(
                                    text = action.actionText.substringBefore(POST) ,
                                    color = primaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Light,
                                )
                                Text(
                                    text = POST,
                                    color = primaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable(
                                        onClick = onPostText
                                    ))
                            }
                        }
                        COMMENT -> {
                            Row {
                                Text(
                                    text = action.actionText.substringBefore(COMMENT) ,
                                    color = primaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Light,
                                )
                                Text(
                                    text = COMMENT,
                                    color = primaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable(
                                        onClick = onCommentText
                                    )
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}