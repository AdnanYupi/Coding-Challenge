package com.codingchallenge.viewControllers.fragments.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.responses.post.PostItem
import com.codingchallenge.repository.posts.PostsRepository

class PostsViewModel(private val postsRepository: PostsRepository): ViewModel() {

    fun getPosts(isOnline: Boolean): LiveData<List<PostItem>> {
        return postsRepository.getPosts(isOnline)
    }

    fun pushPostsToDB(postItems: List<PostItem>) {
        postsRepository.insertPosts(postItems)
    }
}