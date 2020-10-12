package com.codingchallenge.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.model.requests.user.UserRequest
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse
import com.codingchallenge.network.datasource.UsersDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepositoryImpl(
    private val codingChallengeDao: CodingChallengeDao,
    private val usersDataSourceImpl: UsersDataSourceImpl
) : UsersRepository {

    init {
        //Observe for changes on users
        usersDataSourceImpl.apply {
            usersDataSourceImpl.userResponse.observeForever {
                if (it == null)
                    return@observeForever
                AsyncTask.execute {
                    postOfflineUsersOnline()
                }
            }
        }
    }

    //Check is network available
    //If available get users from API
    //else from DB
    override fun getUsers(isOnline: Boolean): LiveData<List<UserData>> {
        if (!isOnline)
            return codingChallengeDao.getUsers()!!
        return usersDataSourceImpl.getUsers()
    }


    /**
     * Probably both requests could be managed in one
     * Adding user in DB sync with API when is possible (immediately or later)
     */
    override fun createNewUserOnline(userRequest: UserRequest) {
        usersDataSourceImpl.createNewUser(userRequest)
    }
    override fun addNewUserOffline(userData: UserData) {
        codingChallengeDao.insertUser(userData)
    }

    //Push users to DB - replace old
    override fun persistUsersOnDB(users: List<UserData>) {
        codingChallengeDao.insertUsers(users)
    }

    //Delete user from API
    //Delete from database
    /**
     * TODO necessary to check was API DELETE successful
     *  if not - delete when is possible (ea. network connection available)
     *  sync with DB
     */
    override fun deleteUser(userId: Int) {
        usersDataSourceImpl.deleteUser(userId)
        AsyncTask.execute{
            codingChallengeDao.deleteUser(userId)
        }
    }

    //Search for users created offline
    //Sync them on server
    private fun postOfflineUsersOnline() {
        if (findOfflineUsers() == null)
            return
        if (findOfflineUsers()!!.isNotEmpty()) {
            val userDataList = findOfflineUsers() ?: return
            for (data in userDataList) {
                val userRequest = UserRequest(
                    data.name!!, data.email!!,
                    data.gender, data.status
                )
                createNewUserOnline(userRequest)
                deleteUser(data.id!!)
            }
        }
    }

    private fun findOfflineUsers(): List<UserData>? {
        return codingChallengeDao.getUsersCreatedOffline()
    }
}