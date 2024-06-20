package com.example.myapp.data.repository

import android.content.Context
import com.example.myapp.data.model.LoginRequest
import com.example.myapp.data.model.LoginResponse
import com.example.myapp.data.remote.AppApi
import com.example.myapp.util.NetWorkResult
import com.example.myapp.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginRepository @Inject constructor(
    private val appApi: AppApi
) {
    suspend fun login(
        context: Context,
        loginRequest: LoginRequest
    ): Flow<NetWorkResult<LoginResponse>> {
        return toResultFlow(context) {
            appApi.login(loginRequest)
        }
    }

}