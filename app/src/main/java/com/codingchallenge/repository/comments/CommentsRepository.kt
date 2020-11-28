package com.codingchallenge.repository.comments

import androidx.lifecycle.LiveData
import com.codingchallenge.model.responses.comment.CommentItem

interface CommentsRepository {
    val comments: LiveData<List<CommentItem>>
    fun getPostComments(postId: Int): LiveData<List<CommentItem>>
}