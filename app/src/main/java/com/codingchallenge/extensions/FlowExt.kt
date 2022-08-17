package com.codingchallenge.extensions

import com.codingchallenge.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> resultFlow(call: suspend () -> Response<T>?): Flow<ApiResult<T>?> {
    return flow {
        emit(ApiResult.Loading)
        try {
            val c = call()
            c?.let {
                if (c.isSuccessful) {
                    emit(ApiResult.Success(c.body()))
                } else {
                    c.errorBody()?.let {
                        val error = it.string()
                        it.close()
                        emit(ApiResult.Error(error))
                    }
                }
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e.toString()))
        }

    }.flowOn(Dispatchers.IO)
}