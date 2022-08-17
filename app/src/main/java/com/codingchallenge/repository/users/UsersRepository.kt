package com.codingchallenge.repository.users

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface UsersRepository {
    fun getUsers(isOnline: Boolean, page: Int): Flow<ApiResult<List<UserItem>>?>
    fun getUser(username: String): Flow<ApiResult<UserItem>?>
    fun persistUsersOnDB(users: List<UserItem>)
}