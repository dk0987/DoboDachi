package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.feature_post.domain.modal.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class FeedScreenStates(
    val posts : Flow<PagingData<Post>> = emptyFlow(),
    val menuExpanded : Boolean = false,
    val selectedOption : String = MenuItems.dropDown[0],
    val isLoading : Boolean = false
)
