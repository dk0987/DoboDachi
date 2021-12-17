package com.devdk.socialmedia.core.util

class DefaultPagination <T> (
    private val onLoadUpdate : (Boolean) -> Unit ,
    private val onRequest : suspend (nextPage : Int) -> Resource<List<T>>,
    private val onSuccess : (itemList : List<T>) -> Unit ,
    private val onError :  suspend (error : String) -> Unit
) : Pagination<T>{

    private var page = 0

    override suspend fun loadItems() {
        onLoadUpdate(true)
        when(val response = onRequest(page)) {
            is Resource.Success -> {
                val items = response.data ?: emptyList()
                page++
                onSuccess(items)
                onLoadUpdate(false)
            }
            is Resource.Error -> {
                val error = response.message!!
                onError(error)
                onLoadUpdate(false)
            }
        }
    }
}