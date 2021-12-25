package com.devdk.socialmedia.feature_auth.data.remote

import com.devdk.socialmedia.core.util.BaseUrl
import com.devdk.socialmedia.feature_auth.data.remote.dto.request.LoginUserRequest
import com.devdk.socialmedia.feature_auth.data.remote.dto.request.RegisterUserRequest
import com.devdk.socialmedia.feature_auth.data.remote.dto.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("api/user/register")
    suspend fun registerUser(
        @Body registerUserRequest: RegisterUserRequest
    ) : AuthResponse

    @POST("api/user/login")
    suspend fun loginUser(
        @Body loginUserRequest: LoginUserRequest
    ) : AuthResponse

    @GET("api/user/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = BaseUrl.AUTH_BASE_URL
    }

}
