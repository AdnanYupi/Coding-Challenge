package com.codingchallenge.repository.users

import com.codingchallenge.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {

    @Provides
    @Singleton
    fun getUsersModule(): UsersRepository {
        return UsersRepositoryImpl(ApiService.invoke())
    }
}