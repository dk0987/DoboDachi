package com.devdk.socialmedia.feature_auth.data.remote

import com.devdk.socialmedia.feature_auth.data.request.LoginUserRequest
import com.devdk.socialmedia.feature_auth.data.request.RegisterUserRequest
import com.devdk.socialmedia.feature_auth.data.response.AuthResponse
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
        const val BASE_URL = "http://192.168.0.148:8080/"
    }

}
