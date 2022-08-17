package com.codingchallenge.model.responses.user

import androidx.room.Dao
import androidx.room.Entity


data class User(
    val userItem: List<UserItem>?
)