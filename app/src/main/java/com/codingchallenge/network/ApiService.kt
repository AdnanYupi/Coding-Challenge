package com.codingchallenge.network

import com.codingchallenge.model.responses.comment.Comment
import com.codingchallenge.model.responses.post.Post
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("users")
    fun getUsersAsync(): Call<User>

    @GET("posts")
    fun getPosts(): Call<Post>

    @GET("comments")
    fun getComments(@Query("postId") id: Int): Call<Comment>


    companion object {
        operator fun invoke(): ApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
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