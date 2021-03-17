package com.codingchallenge.repository.users

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.user.UserItem
import retrofit2.Call
import retrofit2.Response

interface UsersRepository {
    fun getUsers(isOnline: Boolean, page: Int):LiveData<List<UserItem>>
    fun getUser(username: String, onResponse: ((Response<UserItem>) -> Unit))
    fun persistUsersOnDB(users: List<UserItem>)
}