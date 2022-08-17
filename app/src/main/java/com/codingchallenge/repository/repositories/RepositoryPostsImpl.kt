package com.codingchallenge.repository.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.extensions.resultFlow
import com.codingchallenge.model.responses.repositories.Repositories
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.network.ApiService
import kotlinx.coroutines.flow.Flow

class RepositoryPostsImpl(
    private val apiService: ApiService) : RepositoryPosts {

    init {
        /*repositoriesDataSource.apply {
            repositoriesDataSource.repos.observeForever {
                if (it == null)
                    return@observeForever
                *//*AsyncTask.execute {
                    persistRepositories(it)
                }*//*
            }
        }*/
    }

    override fun getRepositories(isOnline: Boolean): Flow<ApiResult<List<RepositoriesItem>>?> {
        //if (isOnline)
            return resultFlow { apiService.getRepositories() }
        //return codingChallengeDatabase.getCodingDao().getRepositoriesPosts()
    }

    override suspend fun persistRepositories(repositories: List<RepositoriesItem>) {
        //TODO codingChallengeDatabase.getCodingDao().insertRepositoryPosts(repositories)
    }
}