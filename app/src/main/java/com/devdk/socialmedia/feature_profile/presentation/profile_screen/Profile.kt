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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_profile.presentation.profile_screen.component.ProfileInfo
import com.devdk.socialmedia.core.presentation.components.StandardFollowButton
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Profile(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val profileState = profileViewModel.profileStates.value
    val state = rememberCollapsingToolbarScaffoldState()
    val profilePic = 45.dp
    val progress = state.toolbarState.progress

    LaunchedEffect(key1 = true ){
        profileViewModel.getProfile(profileViewModel.userId)
        profileViewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message ?: "")
                }
            }
        }
    }

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
                    navController.popBackStack()
                    navController.navigate(Routes.Feed.screen)
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
                        navController.navigate(Routes.EditProfile.screen + "?userId=${profileViewModel.userId}")
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
                contentScale = ContentScale.Crop ,
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
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (profileState.posts.size > 2 || profileState.posts.isEmpty())Arrangement.Center else Arrangement.Top
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
                            color = primaryText,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    profileState.user?.isUser?.let {
                        if (!it){
                            StandardFollowButton(isFollowing = profileState.user.isFollowing) {
                                profileViewModel.onEvent(
                                    ProfileScreenEvents.Follow(
                                        profileState.user.userId ,
                                        profileState.user.isFollowing
                                )   )
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
                        color = primaryText,
                        maxLines = Int.MAX_VALUE
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                ProfileInfo(
                    following = profileState.user?.following ?: 0 ,
                    followers = profileState.user?.followers ?: 0,
                    post = profileState.user?.posts ?: 0,
                    onFollowers = {
                        navController.navigate(Routes.PersonList.screen + "?personList=${Const.FOLLOWERS_SCREEN}&userId=${profileViewModel.userId}")
                    },
                    onFollowing = {
                        navController.navigate(Routes.PersonList.screen + "?personList=${Const.FOLLOWING_SCREEN}&userId=${profileViewModel.userId}")
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.post),
                    color = primaryText,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                )

            }
            items(profileState.posts.size) { i ->
                val post = profileState.posts[i]
                if (i >= profileState.posts.size - 1 && !profileState.endReached && !profileState.isLoading) {
                    profileViewModel.loadPosts()
                }
                Post(
                    post = post,
                    onLike = {
                        profileViewModel.onEvent(ProfileScreenEvents.OnLike(
                            parentId = post.postId,
                            isLiked = post.isLiked
                        ))
                    },
                    onLikedText = {
                        if (post.liked > 0){
                            navController.navigate(
                                Routes.PersonList.screen
                                        + "?personList=${Const.LIKED_SCREEN}&parentId=${post.postId}"
                            )
                        }
                    },
                    onPost = {
                        navController.popBackStack()
                        navController.navigate(Routes.PostDetail.screen + "?postId=${post.postId}")
                    },
                    onComment = {
                        navController.popBackStack()
                        navController.navigate(Routes.PostDetail.screen + "?postId=${post.postId}")
                    },
                    dropDownSelectedItem = { selectedItem ->
                        profileViewModel.onEvent(
                            ProfileScreenEvents.Menu(
                                option = selectedItem,
                                postId = post.postId
                            )
                        )}
                )
            }
            if (profileState.isLoading){
                item { CircularProgressIndicator(color = bottomNavigationItem) }
            }
            else if (!profileState.isLoading && profileState.posts.isEmpty()){
                item {
                    Text(
                        text = stringResource(id = R.string.nothing_to_show),
                        color = primaryText ,
                        fontWeight = FontWeight.Bold ,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            }
        }
    }