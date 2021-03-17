package com.codingchallenge.network.datasource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingchallenge.model.responses.repositories.Repositories
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesDataSourceImpl(private val apiService: ApiService) : RepositoriesDataSource {
    private val _repos = MutableLiveData<List<RepositoriesItem>>()
    override val repos: LiveData<List<RepositoriesItem>>
        get() = _repos

    override fun getRepositories(): LiveData<List<RepositoriesItem>> {
        apiService.getRepositories()
            .enqueue(object : Callback<Repositories> {
                override fun onResponse(
                    call: Call<Repositories>,
                    response: Response<Repositories>
                ) {
                    if (response.isSuccessful)
                        _repos.postValue(response.body())
                }

                override fun onFailure(call: Call<Repositories>, t: Throwable) {
                }

            })
        return _repos
    }

    override fun getUser(username: String, userResponse: ((Response<UserItem>) -> Unit)) {
        apiService.getUser(username)
            .enqueue(object: Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    userResponse.invoke(response)
                }
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                }

            })
    }
}