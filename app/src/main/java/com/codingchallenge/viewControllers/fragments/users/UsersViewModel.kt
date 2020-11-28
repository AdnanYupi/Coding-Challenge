package com.codingchallenge.viewControllers.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.repository.users.UsersRepository

class UsersViewModel(
    private val repository: UsersRepository
) : ViewModel() {

    fun getUsers(isOnline: Boolean): LiveData<List<UserItem>> {
        return repository.getUsers(isOnline)
    }

    fun persistUsersOnDB(userData: List<UserItem>) {
        repository.persistUsersOnDB(userData)
    }
}