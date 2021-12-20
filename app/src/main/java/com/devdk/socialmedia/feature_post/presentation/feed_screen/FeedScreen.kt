package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.presentation.util.TimeStampConverter
import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun Feed(
    navController: NavController,
    feedScreenViewModel: FeedScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val feedScreenStates = feedScreenViewModel.feedScreenStates.value
    val posts = feedScreenViewModel.paginatedPost.value

    LaunchedEffect(key1 = true ){
        feedScreenViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message!!)
                }
                else -> Unit
            }
        }
    }

   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(10.dp),
       horizontalAlignment = CenterHorizontally
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 8.dp),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text(
               text = stringResource(id = R.string.discover),
               fontWeight = FontWeight.Bold,
               fontSize = 35.sp,
               color = primaryText,
           )
           IconButton(
               onClick = {
                   navController.navigate(Routes.Profile.screen)
               },
               Modifier
                   .clip(CircleShape)
                   .size(38.dp)
           ) {
               Image(
                   painter = painterResource(id = R.drawable.profile_pic),
                   contentDescription = stringResource(id = R.string.profile_pic),
                   contentScale = ContentScale.Crop
               )
           }
       }

       Spacer(modifier = Modifier.height(5.dp))
       LazyColumn(
           Modifier
               .fillMaxHeight(0.94f)
               .fillMaxWidth(),
           horizontalAlignment = CenterHorizontally ,
           verticalArrangement = Arrangement.Center
       ) {
             items(posts.items.size) { i ->
                  val post = posts.items[i]
                if (i >= posts.items.size - 1 && !posts.endReached && !posts.isLoading) {
                      feedScreenViewModel.loadMorePost()
                }
                 Post(
                     post = post,
                     onLike = {
                         feedScreenViewModel.onEvent(FeedScreenEvents.OnLike(
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
                     }
                 ) { selectedItem ->
                     feedScreenViewModel.onEvent(
                         FeedScreenEvents.Menu(
                             option = selectedItem,
                             postId = post.postId
                         )
                     )
                 }
                 println(TimeStampConverter().invoke(post.timeStamp * 1000))
                 println(post.timeStamp)
             }
            if (posts.isLoading){
                item { CircularProgressIndicator(color = bottomNavigationItem) }
            }
           else if (!posts.isLoading && posts.items.isEmpty()){
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