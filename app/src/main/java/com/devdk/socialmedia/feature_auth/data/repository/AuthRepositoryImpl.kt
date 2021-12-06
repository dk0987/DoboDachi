package com.devdk.socialmedia.feature_auth.data.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.devdk.socialmedia.core.util.AuthConst
import com.devdk.socialmedia.core.util.Resource
import com.devdk.socialmedia.feature_auth.data.remote.AuthApi
import com.devdk.socialmedia.feature_auth.data.remote.dto.request.LoginUserRequest
import com.devdk.socialmedia.feature_auth.data.remote.dto.request.RegisterUserRequest
import com.devdk.socialmedia.feature_auth.domain.modal.RegisterUser
import com.devdk.socialmedia.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    @SuppressLint("CommitPrefEdits")
    override suspend fun registerUser(registerUser: RegisterUser): Resource<Unit> {
        return try {
            val response =  authApi.registerUser(RegisterUserRequest(
                userName = registerUser.username ,
                eMail = registerUser.email ,
                password = registerUser.password
            ))
            if(response.successful){
                val token = response.token
                sharedPreferences.edit()
                    .putString(AuthConst.token , token)
                    .putString(AuthConst.userId , response.userId)
                    .apply()
                Resource.Success(Unit)
            } else{
                val error = response.message
                Resource.Error(error , Unit)
            }
        }
        catch (e : HttpException){
            Resource.Error("Oops Something Went Wrong", Unit)
        }
        catch (e : IOException){
            Resource.Error("Oops Something Went Wrong" , Unit)
        }

    }

    @SuppressLint("CommitPrefEdits")
    override suspend fun loginUser(email: String, password: String): Resource<Unit> {
        return try {
            val response =  authApi.loginUser(LoginUserRequest(
                eMail = email ,
                password = password
            ))
            if(response.successful){
                val token = response.token
                sharedPreferences.edit()
                    .putString(AuthConst.token , token)
                    .putString(AuthConst.userId , response.userId)
                    .apply()
                Resource.Success(Unit)
            } else{
                val error = response.message
                Resource.Error(error , Unit)
            }
        }
        catch (e : HttpException){
            Resource.Error(e.toString() , Unit)
        }
        catch (e : IOException){
            Resource.Error(e.toString() , Unit)
        }

    }

    override suspend fun authenticate(): Resource<Unit> {
        return try {
            authApi.authenticate()
            Resource.Success(Unit)
        }
        catch (e: IOException) {
            Resource.Error(e.toString(), Unit)
        }
        catch (e: HttpException) {
            Resource.Error(e.toString(), Unit)
        }
    }
}