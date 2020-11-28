package com.codingchallenge.repository.users

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem

interface UsersRepository {
    fun getUsers(isOnline: Boolean):LiveData<List<UserItem>>
    fun persistUsersOnDB(users: List<UserItem>)
}