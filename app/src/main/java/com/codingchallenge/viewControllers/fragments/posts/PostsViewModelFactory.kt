package com.codingchallenge.viewControllers.fragments.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.repository.posts.PostsRepositoryImpl

class PostsViewModelFactory(private val postsRepositoryImpl: PostsRepositoryImpl): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsViewModel(postsRepositoryImpl) as T
    }
}