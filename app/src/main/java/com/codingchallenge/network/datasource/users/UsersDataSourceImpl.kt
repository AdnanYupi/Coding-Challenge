package com.codingchallenge.network.datasource.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDataSourceImpl(private val apiService: ApiService) : UsersDataSource {
    private val userResponseMutableData = MutableLiveData<List<UserItem>>()
    override val userResponse: LiveData<List<UserItem>>
        get() = userResponseMutableData

    override fun getUsers(): LiveData<List<UserItem>> {
        try {
            apiService.getUsersAsync()
                .enqueue(object : Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            userResponseMutableData.postValue(response.body()!!)
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("Failure", t.message)
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userResponseMutableData;
    }

}