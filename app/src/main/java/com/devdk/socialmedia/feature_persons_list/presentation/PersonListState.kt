package com.devdk.socialmedia.feature_persons_list.presentation

import com.devdk.socialmedia.core.domain.modal.PersonList

data class PersonListState(
    val isLoading : Boolean = false,
    val screenName : String = "",
    val people : List<PersonList> = emptyList()
)
