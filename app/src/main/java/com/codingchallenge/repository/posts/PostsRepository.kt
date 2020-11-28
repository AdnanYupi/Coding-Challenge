package com.codingchallenge.repository.posts

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.post.Post
import com.codingchallenge.model.responses.post.PostItem

interface PostsRepository {
    fun getPosts(isOnline: Boolean): LiveData<List<PostItem>>
    fun insertPosts(postItems: List<PostItem>)
}