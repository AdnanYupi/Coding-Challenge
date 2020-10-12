package com.codingchallenge.network.datasource

import androidx.lifecycle.LiveData
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse

interface UsersDataSource {
    val userResponse: LiveData<List<UserData>>
    fun getUsers():LiveData<List<UserData>>
    fun createNewUser(userRequest: UserRequest)
    fun deleteUser(userId: Int)
}