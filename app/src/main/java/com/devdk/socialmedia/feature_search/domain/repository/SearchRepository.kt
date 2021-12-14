package com.devdk.socialmedia.feature_search.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_search.domain.modal.SearchedUser

interface SearchRepository {

    suspend fun searchUser(query : String) : Resource<List<SearchedUser>>
}