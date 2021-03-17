package com.codingchallenge.repository.repositories

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.repositories.RepositoriesItem

interface RepositoryPosts {
    fun getRepositories(isOnline: Boolean): LiveData<List<RepositoriesItem>>
    fun persistRepositories(repositories: List<RepositoriesItem>)
}