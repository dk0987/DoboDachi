package com.devdk.socialmedia.feature_chat.presentation.message_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.core.presentation.ui.theme.background
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@Composable
fun RemoteChatBubble(
    message : String,
    timeStamp : Long,
    triangleHeight : Dp = 8.dp,
    triangleWidth : Dp = 10.dp
) {
    Box(modifier = Modifier
        .padding(horizontal = 15.dp , vertical = 5.dp)
        .fillMaxWidth(0.85f)
        .drawBehind {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0 - triangleWidth.toPx() , 0f)
                lineTo(0f, 0f + triangleHeight.toPx())
                close()
            }
            drawPath(
                path = path ,
                color = primaryText
            )
        },
    ){
        Column(
            horizontalAlignment = Alignment.End ,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp
                    )
                ).background(primaryText)
                .defaultMinSize(
                    minWidth = 100.dp,
                    minHeight = 40.dp)
        ) {
            Text(
                text = message,
                color = background,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp,)
                    .align(Alignment.End)
            )

            Text(
                text = "4:00 P.M",
                color = background,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp , bottom = 5.dp)
                    .align(Alignment.End),
                maxLines = 1
            )
        }
    }
}