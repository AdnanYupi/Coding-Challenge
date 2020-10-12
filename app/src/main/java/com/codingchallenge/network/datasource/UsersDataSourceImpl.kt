package com.codingchallenge.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse
import com.codingchallenge.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class UsersDataSourceImpl(private val apiService: ApiService) : UsersDataSource {
    private val userResponseMutableData = MutableLiveData<List<UserData>>()
    override val userResponse: LiveData<List<UserData>>
        get() = userResponseMutableData

    override fun getUsers(): LiveData<List<UserData>> {
        try {
            apiService.getUsersAsync()
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            userResponseMutableData.postValue(response.body()!!.userData)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Failure", t.message)
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userResponseMutableData;
    }

    override fun createNewUser(userRequest: UserRequest) {
        try {
            apiService.createNewUser(userRequest)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful)
                            userResponseMutableData.postValue(response.body()!!.userData)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Failure", t.message);
                    }

                })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun deleteUser(userId: Int) {
        try {
            apiService.deleteUser(userId)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                       //TODO handle cases when deleting wasn't successful
                        if (response.isSuccessful)
                            userResponseMutableData.postValue(response.body()!!.userData)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}