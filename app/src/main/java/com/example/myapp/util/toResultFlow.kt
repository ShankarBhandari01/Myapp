package com.example.myapp.util


import android.content.Context
import com.example.myapp.util.Constants.Companion.API_INTERNET_MESSAGE
import com.example.myapp.util.Utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


inline fun <reified T> toResultFlow(
    context: Context, crossinline call: suspend () -> Response<T>?
): Flow<NetWorkResult<T>> {
    return flow {
        val isInternetConnected = Utils.hasInternetConnection(context)
                //&& isOnline()

        if (isInternetConnected) {
            emit(NetWorkResult.Loading(true))
            val c = call()
            c?.let { response ->
                try {
                    if (c.isSuccessful && c.body() != null) {
                        c.body()?.let {
                            emit(NetWorkResult.Success(it))
                        }
                    } else {
                        if (c.code() != 500) {
                            emit(NetWorkResult.Error(null, response.message()))
                        } else {
                            emit(NetWorkResult.Error(null, response.message()))
                        }
                    }
                } catch (e: Exception) {
                    emit(NetWorkResult.Error(null, e.message))
                }
            }
        } else {
            emit(NetWorkResult.Error(null, API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}

