package com.codingchallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse

@Dao
interface CodingChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(userData: List<UserData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userData: UserData): Long?

    @Query("SELECT * FROM user_data")
    fun getUsers(): LiveData<List<UserData>>?

    @Query("SELECT * FROM user_data WHERE isCreatedOffline = 1")
    fun getUsersCreatedOffline(): List<UserData>?

    @Query("DELETE FROM user_data WHERE id = :userId")
    fun deleteUser(userId: Int)
}