package com.example.myapp.di

import android.content.Context
import com.example.myapp.util.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor(var context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request = chain.request()

        newRequest = newRequest.newBuilder()
            .addHeader(
                "Token",
                if (Constants.API_KEY.isEmpty()) "" else "Bearer ${Constants.API_KEY}"
            ).addHeader(
                "platform",
                Constants.PLATFORM
            )
            .build()

        return chain.proceed(newRequest)
    }
}

