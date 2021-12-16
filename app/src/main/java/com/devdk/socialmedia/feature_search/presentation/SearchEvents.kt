package com.devdk.socialmedia.feature_search.presentation

sealed class SearchEvents{
    data class Follow(val userId : String , val isFollowed : Boolean) : SearchEvents()
}
