package com.codingchallenge.model.responses.repositories

import com.google.gson.annotations.SerializedName

data class Repositories(
    @SerializedName("repositories")
    val repositories: List<RepositoriesItem>?
)