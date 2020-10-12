package com.codingchallenge.model.responses.generic

import androidx.room.Embedded

data class Meta(
    @Embedded
    val pagination: Pagination
)