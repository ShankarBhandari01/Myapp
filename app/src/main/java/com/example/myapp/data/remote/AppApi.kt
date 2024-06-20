package com.example.myapp.data.remote


import com.example.myapp.data.model.LoginRequest
import com.example.myapp.data.model.LoginResponse
import com.example.myapp.util.Constants.Companion.API_LOGON
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    @POST(API_LOGON)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}