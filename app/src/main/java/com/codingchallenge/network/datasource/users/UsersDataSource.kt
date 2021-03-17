package com.codingchallenge.network.datasource.users

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.user.UserItem
import retrofit2.Response

interface UsersDataSource {
    val userResponse: LiveData<List<UserItem>>
    fun getUsers(page: Int):LiveData<List<UserItem>>
    fun getUser(username: String, userResponse: ((Response<UserItem>) -> Unit))
}