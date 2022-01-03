package com.devdk.socialmedia.feature_chat.presentation.chats_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_chat.presentation.chats_screen.component.ChatFollowerList
import com.devdk.socialmedia.feature_chat.presentation.chats_screen.component.ChatList
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Chats(
    navController: NavController ,
    scaffoldState: ScaffoldState,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val progress = collapsingToolbarState.toolbarState.progress
    val chatSearchState = chatViewModel.chatSearchTextFieldState.value
    val chatState = chatViewModel.chatState.value

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = collapsingToolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Spacer(modifier = Modifier.height(0.3.dp))
            Text(
                text = stringResource(id = R.string.chat),
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = primaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = progress
                    },
                textAlign = TextAlign.Center,
                letterSpacing = 4.sp
            )
        },
        enabled = true
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(0.94f),
            horizontalAlignment = Alignment.CenterHorizontally ,
        ) {
            stickyHeader {
                StandardTextField(
                    value = chatSearchState.text,
                    isError = chatSearchState.isError,
                    placeholder = stringResource(id = R.string.search_friend_for_chat),
                    error = chatSearchState.error,
                    onValueChange = {
                        chatViewModel.onQueryTextChange(it)
                    },
                    trailingIcon = Icons.Outlined.Search,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item{
                Text(
                    text = stringResource(id = R.string.following_small),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp ,
                    color = primaryText ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 10.dp),
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Start
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    items(chatState.followingsForChat){ followings ->
                        ChatFollowerList(
                            chatFollowings = followings
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(20){
                ChatList(
                    senderProfilePic = painterResource(id = R.drawable.mhawallparer),
                    receiverProfilePic = painterResource(id = R.drawable.profile_pic),
                    username = "Yuji Itadori",
                    isSeen = true,
                    isTyping = false,
                    lastMessage = "Hii bro how are you there saw your message today nice to se you are alive i am coming home soon ",
                    onClick = {
                        navController.navigate(Routes.Messages.screen)
                    }
                )
            }
        }
    }

}