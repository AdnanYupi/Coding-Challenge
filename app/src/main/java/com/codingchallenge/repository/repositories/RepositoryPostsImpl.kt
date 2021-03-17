package com.codingchallenge.repository.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.network.datasource.repositories.RepositoriesDataSource

class RepositoryPostsImpl(
    private val repositoriesDataSource: RepositoriesDataSource,
    private val codingChallengeDatabase: CodingChallengeDatabase
) : RepositoryPosts {

    init {
        repositoriesDataSource.apply {
            repositoriesDataSource.repos.observeForever {
                if (it == null)
                    return@observeForever
                AsyncTask.execute {
                    persistRepositories(it)
                }
            }
        }
    }

    override fun getRepositories(isOnline: Boolean): LiveData<List<RepositoriesItem>> {
        if (isOnline)
            return repositoriesDataSource.getRepositories()
        return codingChallengeDatabase.getCodingDao().getRepositoriesPosts()
    }

    override fun persistRepositories(repositories: List<RepositoriesItem>) {
        codingChallengeDatabase.getCodingDao().insertRepositoryPosts(repositories)
    }
}