package com.codingchallenge.network.datasource.users

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem

interface UsersDataSource {
    val userResponse: LiveData<List<UserItem>>
    fun getUsers():LiveData<List<UserItem>>
}