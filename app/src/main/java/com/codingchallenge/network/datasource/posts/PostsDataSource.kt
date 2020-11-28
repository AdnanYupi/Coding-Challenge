package com.codingchallenge.network.datasource.posts

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.post.PostItem

interface PostsDataSource {
    val postsResponse: LiveData<List<PostItem>>
    fun getPosts(): LiveData<List<PostItem>>
}