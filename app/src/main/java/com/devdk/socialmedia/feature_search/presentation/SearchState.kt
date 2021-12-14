package com.devdk.socialmedia.feature_search.presentation

import com.devdk.socialmedia.feature_search.domain.modal.SearchedUser

data class SearchState(
    val searchedResult : List<SearchedUser> = emptyList(),
    val isLoading : Boolean = false
)
