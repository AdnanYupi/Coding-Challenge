package com.codingchallenge.network

import androidx.lifecycle.LiveData
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserResponse
import com.codingchallenge.network.interceptor.AuthInterceptorImpl
import com.codingchallenge.util.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("public-api/users")
    fun getUsersAsync(): Call<UserResponse>

    @POST("public-api/users")
    fun createNewUser(@Body userRequest: UserRequest): Call<UserResponse>

    @DELETE("public-api/users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<UserResponse>


    companion object {
        operator fun invoke(): ApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptorImpl())
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
    }
}