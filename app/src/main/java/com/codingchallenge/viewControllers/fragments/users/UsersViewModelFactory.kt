package com.codingchallenge.viewControllers.fragments.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.repository.users.UsersRepositoryImpl

class UsersViewModelFactory(private val usersRepositoryImpl:
                            UsersRepositoryImpl
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(usersRepositoryImpl) as T
    }

}