package com.devdk.socialmedia.feature_post.presentation.post_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.*
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.LikedOn
import com.devdk.socialmedia.feature_post.presentation.feed_screen.FeedScreenEvents
import com.devdk.socialmedia.feature_post.presentation.post_detail_screen.component.Comment

@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterialApi
@Composable
fun PostDetail(
    navController: NavController,
    postDetailViewModel: PostDetailViewModel = hiltViewModel()
) {
    val commentTextFieldState = postDetailViewModel.commentTextFieldStates.value
    val postDetailStates = postDetailViewModel.postDetailStates.value
    val commentStates = postDetailViewModel.commentStates.value

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
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (postDetailStates.isPostLoading) {
                item {
                    CircularProgressIndicator(color = bottomNavigationItem)
                }
            }
            item {
                postDetailStates.post?.let { post ->
                    Post(
                        post = post,
                        maxLines = Int.MAX_VALUE,
                        onProfilePic = {
                            navController.navigate(Routes.Profile.screen)
                        },
                        onLike = {
                            postDetailViewModel.onEvent(
                                PostDetailEvents.OnLike(
                                parentId = post.postId,
                                isLiked = post.isLiked ,
                                parentType = LikedOn.Post
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
                    )
                }
                StandardTextField(
                    value = commentTextFieldState.text,
                    onValueChange = {
                        postDetailViewModel.onEvent(
                            PostDetailEvents.CommentTextField(
                                it
                            )
                        )
                    },
                    trailingIcon = Icons.Outlined.Send,
                    onTrailingIcon = {
                        postDetailViewModel.onEvent(PostDetailEvents.Comment)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(commentStates.comments){ comment ->
                Comment(
                    comment = comment,
                    dropDownSelectedItem = { menuItem ->
                        postDetailViewModel.onEvent(
                            PostDetailEvents.Menu(
                                menuItem,
                                comment.commentId
                            )
                        )
                    },
                    onLike = {
                        postDetailViewModel.onEvent(
                            PostDetailEvents.OnLike(
                                parentId = comment.commentId ,
                                isLiked = comment.isLiked ,
                                parentType = LikedOn.Comment
                            )
                        )
                    }
                )
            }

            if (commentStates.isCommentLoading) {
                item {
                    CircularProgressIndicator(color = bottomNavigationItem)
                }
            }
        }
    }
}