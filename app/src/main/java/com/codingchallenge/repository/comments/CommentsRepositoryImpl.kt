package com.codingchallenge.repository.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingchallenge.model.responses.comment.Comment
import com.codingchallenge.model.responses.comment.CommentItem
import com.codingchallenge.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CommentsRepositoryImpl(private val apiService: ApiService) : CommentsRepository {
    private val _comments = MutableLiveData<List<CommentItem>>()
    override val comments: LiveData<List<CommentItem>>
        get() = _comments

    override fun getPostComments(postId: Int): LiveData<List<CommentItem>> {
        try {
            val call = apiService.getComments(postId)
            call.enqueue(object: Callback<Comment> {
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    if (response.isSuccessful && response.body() != null)
                        _comments.postValue(response.body()!!)
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _comments
    }
}