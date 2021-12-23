package com.devdk.socialmedia.feature_profile.presentation.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_profile.presentation.profile_screen.component.ProfileInfo
import com.devdk.socialmedia.core.presentation.components.StandardFollowButton
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@ExperimentalMaterialApi
@Composable
fun Profile(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState = profileViewModel.profileStates.value
    val state = rememberCollapsingToolbarScaffoldState()
    val profilePic = 45.dp
    val progress = state.toolbarState.progress

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        enabled = true ,
        toolbar = {
            Spacer(modifier = Modifier.height(45.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(onClick = {
                    navController.navigateUp()
                },
                    modifier = Modifier
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.back),
                        tint = primaryText
                    )
                }

                if (profileState.user?.isUser == true){
                    IconButton(onClick = {
                        navController.navigate(Routes.EditProfile.screen)
                    },
                        modifier = Modifier
                            .size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            tint = primaryText
                        )
                    }
                }

            }
            Image(
                painter = rememberImagePainter(data = profileState.user?.bannerUrl, builder = {
                    crossfade(true)
                    size(OriginalSize)
                }),
                contentDescription = stringResource(id = R.string.banner),
                modifier = Modifier
                    .parallax(0.1f)
                    .fillMaxWidth()
                    .height(200.dp)
                    .graphicsLayer {
                        alpha = progress
                    },
                contentScale = ContentScale.FillWidth ,
                alpha = 0.7f
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp + profilePic)
                .padding(horizontal = 40.dp)
                .parallax(0.1f)
                .graphicsLayer {
                    alpha = progress
                },
                contentAlignment = BottomCenter
            ){
                Image(
                    painter = rememberImagePainter(
                        data = profileState.user?.profileUrl,
                        builder = {
                            crossfade(true)
                            size(OriginalSize)
                        }),
                    contentDescription = stringResource(id = R.string.profile_pic),
                    modifier = Modifier
                        .size(profilePic * progress + 35.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop ,
                )
            }

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    profileState.user?.username?.let {
                        Text(
                            text = it,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            color = primaryText,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    profileState.user?.isUser?.let {
                        if (!it){
                            StandardFollowButton(isFollowing = profileState.user.isFollowing) {

                            }
                        }

                    }

                }

            }

            item {
                Spacer(modifier = Modifier.height(5.dp))
                profileState.user?.bio?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = primaryText
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                ProfileInfo(
                    following = profileState.user?.following ?: 0 ,
                    followers = profileState.user?.followers ?: 0,
                    post = profileState.user?.posts ?: 0,
                    onFollowers = {
                        navController.navigate(Routes.PersonList.screen + "?personList=Followers")
                    },
                    onFollowing = {
                        navController.navigate(Routes.PersonList.screen + "?personList=Following")
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.post),
                    color = primaryText,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

            }
            items (5){
//                Post(
//                    username = "Izuku Midoriya",
//                    profilePic = painterResource(id = R.drawable.profile_pic),
//                    postImage = painterResource(id = R.drawable.mhawallparer),
//                    numberOfLike = 45,
//                    numberOfComment = 7,
//                    isLiked = true,
//                    onPost = {
//                        navController.navigate(Routes.PostDetail.screen)
//                    },
//                    onComment = {
//                        navController.navigate(Routes.PostDetail.screen)
//                    },
//                    onLikedText = {
//                        navController.navigate(Routes.PersonList.screen)
//                    },
//                    isUser = true,
//                    description = "used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret"
//                )
            }
        }
    }

}