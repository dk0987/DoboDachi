package com.devdk.socialmedia.feature_search.data.repository

import com.devdk.socialmedia.core.util.Const
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_search.data.remote.SearchApi
import com.devdk.socialmedia.feature_search.domain.modal.SearchedUser
import com.devdk.socialmedia.feature_search.domain.repository.SearchRepository
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchUser(query: String): Resource<List<SearchedUser>> {
        val searchedUser = arrayListOf<SearchedUser>()
        return try {
            val response = searchApi.searchUser(query)
            if (response.successful){
                response.data?.forEach { searchedUserResponse ->
                    searchedUser.add(
                        SearchedUser(
                            username = searchedUserResponse.username,
                            userId = searchedUserResponse.userId ,
                            profilePicUrl = searchedUserResponse.profileUrl ,
                            description = searchedUserResponse.description ,
                            isFollowing = searchedUserResponse.isFollowing
                        )
                    )
                }
                 Resource.Success(searchedUser)
            }
            else {
                 Resource.Error( response.message , searchedUser)
            }
        }
        catch (e : IOException){
             Resource.Error( Const.SOMETHING_WRONG, searchedUser)
        }
        catch (e : HttpException){
             Resource.Error( Const.SOMETHING_WRONG, searchedUser)
        }
    }
}