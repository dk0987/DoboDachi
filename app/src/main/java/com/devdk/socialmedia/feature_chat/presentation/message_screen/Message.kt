package com.devdk.socialmedia.feature_chat.presentation.message_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.feature_chat.presentation.message_screen.component.OwnerChatBubble
import com.devdk.socialmedia.feature_chat.presentation.message_screen.component.RemoteChatBubble

@ExperimentalFoundationApi
@Composable
fun Message(
    navController: NavController ,
    scaffoldState: ScaffoldState ,
    messageViewModel: MessageViewModel = hiltViewModel()
) {
    val messageTextState = messageViewModel.messageTextFieldState.value
    val messageState = messageViewModel.messageState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn{
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(container)
                        .height(55.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        },
                            modifier = Modifier
                                .size(30.dp)
                                .padding(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowLeft,
                                contentDescription = stringResource(id = R.string.back),
                                tint = primaryText
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.mhawallparer),
                            contentDescription = stringResource(
                                id = R.string.profile_pic
                            ),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Yuji Itadori",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = primaryText,
                                maxLines = 1 ,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            if (false){
                                Box(modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                                )
                            }
                        }
                        if (false){
                            Text(
                                text = stringResource(id = R.string.typing),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = primaryText,
                                maxLines = 1 ,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                    }
                }
            }

        }
        LazyColumn(
            reverseLayout = true,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.91f)
        ){
            items(messageState.messages.size){ i ->
                val message = messageState.messages[i]
                if (i >= messageState.messages.size - 1 && !messageState.endReached && !messageState.isLoading) {
                    messageViewModel.loadNextMessages()
                }
                if (message.userId == messageViewModel.toUserID){
                    RemoteChatBubble(message = message.message, timeStamp = message.timeStamp)
                }
                else {
                    OwnerChatBubble(message = message.message, timeStamp = message.timeStamp)
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp)
            .background(Color.Transparent)
        ){
            StandardTextField(
                value = messageTextState.text,
                isError = messageTextState.isError,
                placeholder = stringResource(id = R.string.enter_message),
                error = messageTextState.error,
                onValueChange = {
                    messageViewModel.onEvent(MessageEvents.Message(it))
                },
                trailingIcon = Icons.Outlined.Send,
                roundedCornerShape = 25.dp,
                onTrailingIcon = {
                    messageViewModel.onEvent(MessageEvents.SendMessage)
                }
            )
        }


    }

}