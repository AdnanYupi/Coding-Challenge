package com.codingchallenge.viewControllers.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.repository.users.UsersRepository
import retrofit2.Response

class UsersViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    fun getUsers(isOnline: Boolean, page: Int): LiveData<List<UserItem>> {
        return repository.getUsers(isOnline, page)
    }

    fun getUser(username: String, userResponse: ((Response<UserItem>) -> Unit)) {
        repository.getUser(username, userResponse)
    }

    fun persistUsersOnDB(userData: List<UserItem>) {
        repository.persistUsersOnDB(userData)
    }
}