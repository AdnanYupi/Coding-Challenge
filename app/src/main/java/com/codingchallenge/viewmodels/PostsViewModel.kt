package com.codingchallenge.viewmodels

import androidx.lifecycle.*
import com.codingchallenge.model.responses.repositories.Repositories
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.repository.repositories.RepositoryPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val postsRepository: RepositoryPosts): ViewModel() {

    var postItems = arrayListOf<RepositoriesItem>()

    fun getPosts(isOnline: Boolean): LiveData<ApiResult<List<RepositoriesItem>>?> {
        return postsRepository.getRepositories(isOnline).asLiveData()
    }

   /* fun pushPostsToDB(postItems: List<PostItem>) {
        postsRepository.insertPosts(postItems)
    }*/
}