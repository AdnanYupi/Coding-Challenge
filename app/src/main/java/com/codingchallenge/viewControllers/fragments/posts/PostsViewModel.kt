package com.codingchallenge.viewControllers.fragments.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.repository.repositories.RepositoryPosts

class PostsViewModel(private val postsRepository: RepositoryPosts): ViewModel() {

    fun getPosts(isOnline: Boolean): LiveData<List<RepositoriesItem>> {
        return postsRepository.getRepositories(isOnline)
    }

   /* fun pushPostsToDB(postItems: List<PostItem>) {
        postsRepository.insertPosts(postItems)
    }*/
}