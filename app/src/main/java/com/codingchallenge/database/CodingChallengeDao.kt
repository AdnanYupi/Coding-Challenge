package com.codingchallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.model.responses.user.UserItem

@Dao
interface CodingChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(userData: List<UserItem>)

    @Query("SELECT * FROM useritem")
    fun getUsers(): LiveData<List<UserItem>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryPosts(postItems: List<RepositoriesItem>)

    @Query("SELECT * FROM RepositoriesItem")
    fun getRepositoriesPosts(): LiveData<List<RepositoriesItem>>

}