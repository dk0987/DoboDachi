package com.devdk.socialmedia.feature_search.domain.use_case

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_search.domain.modal.SearchedUser
import com.devdk.socialmedia.feature_search.domain.repository.SearchRepository

class SearchUserUseCase(
    private val  searchRepository: SearchRepository
) {
    suspend operator fun invoke(query : String) : Resource<List<SearchedUser>>{
        return searchRepository.searchUser(query)
    }
}