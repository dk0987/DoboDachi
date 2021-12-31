package com.devdk.socialmedia.feature_chat.presentation.chats_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@ExperimentalMaterialApi
@Composable
fun ChatList(
    senderProfilePic : Painter,
    receiverProfilePic : Painter,
    username : String ,
    lastMessage : String ,
    isTyping : Boolean = true ,
    isSeen : Boolean = false ,
    senderProfilePicSize : Dp = 50.dp,
    receiverProfilePicSize : Dp = 20.dp,
    elevation : Dp = 10.dp ,
    background : Color = container,
    textColor : Color = primaryText,
    roundedCornerShape : Dp = 15.dp,
    chatCardSize : Dp = 65.dp,
    onClick : () -> Unit = {}
) {
    Card(
        onClick = onClick,
        backgroundColor = background ,
        elevation = elevation,
        shape = RoundedCornerShape(roundedCornerShape),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .defaultMinSize(minHeight = chatCardSize)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically
            ) {
                Image(
                    painter = senderProfilePic,
                    contentDescription = stringResource(id = R.string.sender_profile_pic),
                    modifier = Modifier
                        .size(senderProfilePicSize)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop ,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.Start , modifier = Modifier.fillMaxWidth(0.76f)) {
                    Text(
                        text = username,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        maxLines = 1 ,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = lastMessage,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = textColor,
                        maxLines = 2 ,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.offset(y = chatCardSize - senderProfilePicSize)
            ) {
                if (isSeen){
                    Image(
                        painter = receiverProfilePic,
                        contentDescription = stringResource(id = R.string.sender_profile_pic),
                        modifier = Modifier
                            .size(receiverProfilePicSize)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop ,
                    )
                }
                if (isTyping){
                    Text(
                        text = "Typing...",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = textColor,
                        maxLines = 1 ,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
    }
}