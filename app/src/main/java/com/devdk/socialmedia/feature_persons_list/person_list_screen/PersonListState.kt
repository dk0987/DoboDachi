package com.devdk.socialmedia.feature_persons_list.person_list_screen

import com.devdk.socialmedia.core.domain.modal.GetLikes

data class PersonListState(
    val isLoading : Boolean = false,
    val screenName : String = "",
    val persons : List<GetLikes> = emptyList()
)
