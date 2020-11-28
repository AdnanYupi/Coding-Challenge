package com.codingchallenge.viewControllers.activities.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codingchallenge.model.responses.comment.CommentItem
import com.codingchallenge.repository.comments.CommentsRepository

class CommentsViewModel(private val commentsRepository: CommentsRepository): ViewModel() {

    fun getComments(id: Int): LiveData<List<CommentItem>> {
        return commentsRepository.getPostComments(id)
    }
}