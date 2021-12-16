package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.*
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_post.presentation.post_detail_screen.component.Comment
import okhttp3.Route

@ExperimentalMaterialApi
@Composable
fun PostDetail(
    navController: NavController,
    postDetailViewModel: PostDetailViewModel = hiltViewModel()
) {
    val commentState = postDetailViewModel.commentTextFieldStates.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        },
            modifier = Modifier
                .size(30.dp)
                .padding(start = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = stringResource(id = R.string.back),
                tint = primaryText
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
//                Post(
//                    username = "Izuku Midoriya",
//                    profilePic = painterResource(id = R.drawable.post_pic),
//                    postImage = painterResource(id = R.drawable.profile_pic),
//                    numberOfLike = 45,
//                    numberOfComment = 7,
//                    maxLines = 200,
//                    onProfilePic = {
//                        navController.navigate(Routes.Profile.screen)
//                    },
//                    description = "used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regretused in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret"
//                )
                StandardTextField(
                    value = commentState.text,
                    onValueChange = {postDetailViewModel.onEvent(PostDetailEvents.Comment(it))},
                    trailingIcon = Icons.Outlined.Send
                )
            }
            items(2){
                Comment(
                    profileURL = painterResource(id = R.drawable.profile_pic),
                    username = "Izuku Midoriya",
                    timeStamp = 0,
                    isLiked = true,
                    liked = 20,
                    onProfile = {
                        navController.navigate(Routes.Profile.screen)
                    },
                    comment = "used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret"
                )
            }
        }
    }
}