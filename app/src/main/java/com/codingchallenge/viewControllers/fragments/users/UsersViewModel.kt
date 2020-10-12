package com.codingchallenge.viewControllers.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.repository.UsersRepository

class UsersViewModel(
    private val repository: UsersRepository ) : ViewModel() {

    fun getUsers(isOnline: Boolean): LiveData<List<UserData>> {
        return repository.getUsers(isOnline)
    }

    fun createNewUserOffline(userData: UserData) {
        repository.addNewUserOffline(userData)
    }

    fun createNewUserOnline(userData: UserData) {
        val userRequest = UserRequest(userData.name!!, userData.email!!, userData.gender, userData.status)
        repository.createNewUserOnline(userRequest)
    }

    fun deleteUser(userId: Int) {
        repository.deleteUser(userId)
    }

    fun persistUsersOnDB(userData: List<UserData>) {
        repository.persistUsersOnDB(userData)
    }
}