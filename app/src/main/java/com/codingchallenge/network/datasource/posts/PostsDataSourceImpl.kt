package com.codingchallenge.network.datasource.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingchallenge.model.responses.post.Post
import com.codingchallenge.model.responses.post.PostItem
import com.codingchallenge.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsDataSourceImpl(private val apiService: ApiService) : PostsDataSource {
    private val _postResponse = MutableLiveData<List<PostItem>>()
    override val postsResponse: LiveData<List<PostItem>>
        get() = _postResponse

    override fun getPosts(): LiveData<List<PostItem>> {
        try {
           val call = apiService.getPosts()
            call.enqueue(object: Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful && response.body() != null) {
                        _postResponse.postValue(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                }

            })
        } catch (e: Exception) {

        }
        return _postResponse
    }
}