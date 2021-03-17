package com.codingchallenge.network.datasource.repositories

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.model.responses.user.UserItem
import retrofit2.Response

interface RepositoriesDataSource {
    val repos: LiveData<List<RepositoriesItem>>
    fun getRepositories(): LiveData<List<RepositoriesItem>>
    fun getUser(username: String, userResponse: ((Response<UserItem>) -> Unit))
}