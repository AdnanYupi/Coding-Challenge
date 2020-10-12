package com.codingchallenge.model.requests.user

data class UserRequest(
    val name: String,
    val email: String,
    val gender: String?,
    val status: String?
)