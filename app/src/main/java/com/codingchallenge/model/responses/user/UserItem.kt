package com.codingchallenge.model.responses.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserItem(
    val email: String?,
    @PrimaryKey
    val id: Int,
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
)