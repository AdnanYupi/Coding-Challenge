package com.codingchallenge.viewControllers.activities.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.repository.comments.CommentsRepositoryImpl

class CommentsViewModelFactory(private val commentsRepositoryImpl: CommentsRepositoryImpl): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentsViewModel(commentsRepositoryImpl) as T
    }
}