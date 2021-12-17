package com.devdk.socialmedia.core.util

interface Pagination<T> {

    suspend fun loadItems()

}