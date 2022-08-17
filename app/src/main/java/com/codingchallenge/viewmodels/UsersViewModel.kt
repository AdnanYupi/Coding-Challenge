package com.codingchallenge.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.repository.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

    var usersList = arrayListOf<UserItem>()

    fun getUsers(isOnline: Boolean, page: Int): LiveData<ApiResult<List<UserItem>>?> {
        return repository.getUsers(isOnline, page).asLiveData()
    }

    fun getUser(username: String): LiveData<ApiResult<UserItem>?> {
        return repository.getUser(username).asLiveData()
    }

    fun persistUsersOnDB(userData: List<UserItem>) {
        repository.persistUsersOnDB(userData)
    }
}