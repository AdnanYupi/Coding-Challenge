package com.codingchallenge.repository.repositories

import com.codingchallenge.network.ApiService
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsRepositoryModule {

    @Provides
    @Singleton
    fun getPostsModule(): RepositoryPosts {
        return RepositoryPostsImpl(ApiService.invoke())
    }
}