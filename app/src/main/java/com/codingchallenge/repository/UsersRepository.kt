package com.codingchallenge.repository

import androidx.lifecycle.LiveData
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse

interface UsersRepository {
    fun getUsers(isOnline: Boolean):LiveData<List<UserData>>
    fun createNewUserOnline(userRequest: UserRequest)
    fun addNewUserOffline(userData: UserData)
    fun persistUsersOnDB(users: List<UserData>)
    fun deleteUser(userId: Int)
}