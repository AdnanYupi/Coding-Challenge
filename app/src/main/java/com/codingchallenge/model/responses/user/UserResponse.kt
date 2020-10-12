package com.codingchallenge.model.responses.user

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val userData: List<UserData>?)