package com.codingchallenge.repository.posts

import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.model.responses.post.Post
import com.codingchallenge.model.responses.post.PostItem
import com.codingchallenge.network.datasource.posts.PostsDataSource

class PostsRepositoryImpl(
    private val codingChallengeDao: CodingChallengeDao,
    private val postsDataSource: PostsDataSource
) : PostsRepository {

    init {
        postsDataSource.apply {
            postsDataSource.postsResponse.observeForever {
                if (it == null)
                    return@observeForever
            }
        }
    }

    override fun getPosts(isOnline: Boolean): LiveData<List<PostItem>> {
       if (isOnline)
          return postsDataSource.getPosts()
        return codingChallengeDao.getPosts()
    }

    override fun insertPosts(postItems: List<PostItem>) {
        codingChallengeDao.insertPosts(postItems)
    }
}