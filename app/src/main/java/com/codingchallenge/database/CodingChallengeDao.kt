package com.codingchallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingchallenge.model.responses.post.Post
import com.codingchallenge.model.responses.post.PostItem
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem

@Dao
interface CodingChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(userData: List<UserItem>)

    @Query("SELECT * FROM useritem")
    fun getUsers(): LiveData<List<UserItem>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(postItems: List<PostItem>)

    @Query("SELECT * FROM postitem")
    fun getPosts(): LiveData<List<PostItem>>

}