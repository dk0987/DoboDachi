package com.devdk.socialmedia.feature_auth.domain.repository

import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.domain.modal.RegisterUser

interface AuthRepository {

    suspend fun registerUser(registerUser: RegisterUser) : Resource<Unit>

    suspend fun loginUser(email : String , password : String) : Resource<Unit>

    suspend fun authenticate() : Resource<Unit>
}