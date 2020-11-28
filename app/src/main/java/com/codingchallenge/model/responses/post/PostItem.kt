package com.codingchallenge.model.responses.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostItem(
    val body: String?,
    @PrimaryKey
    val id: Int,
    val title: String?,
    val userId: Int
)