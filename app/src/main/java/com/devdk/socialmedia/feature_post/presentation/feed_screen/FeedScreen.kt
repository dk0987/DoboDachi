package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_auth.presentation.util.UiEvent
import com.devdk.socialmedia.feature_post.domain.modal.Post
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun Feed(
    navController: NavController,
    feedScreenViewModel: FeedScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val feedScreenStates = feedScreenViewModel.feedScreenStates.value
    val posts = feedScreenViewModel.paginatedPost.value
    val scope = rememberCoroutineScope()

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
                  post = post
              )
          }
       }
   }
}