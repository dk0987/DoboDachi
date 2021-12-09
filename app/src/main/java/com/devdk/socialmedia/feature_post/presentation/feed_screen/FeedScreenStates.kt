package com.devdk.socialmedia.feature_post.presentation.feed_screen

import com.devdk.socialmedia.core.presentation.util.MenuItems

data class FeedScreenStates(
////    val posts : List<Post> = emptyList()
    val menuExpanded : Boolean = false,
    val selectedOption : String = MenuItems.dropDown[0],

)
