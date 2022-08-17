package com.codingchallenge.repository.repositories

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.repositories.Repositories
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface RepositoryPosts {
    fun getRepositories(isOnline: Boolean): Flow<ApiResult<List<RepositoriesItem>>?>
    suspend fun persistRepositories(repositories: List<RepositoriesItem>)
}