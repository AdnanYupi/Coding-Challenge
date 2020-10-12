package com.codingchallenge.model.responses.generic

data class Pagination(
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)