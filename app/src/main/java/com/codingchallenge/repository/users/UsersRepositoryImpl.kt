package com.codingchallenge.repository.users

import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.datasource.users.UsersDataSourceImpl
import retrofit2.Response

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
            }
        }
    }

    //Check is network available
    //If available get users from API
    //else from DB
    override fun getUsers(isOnline: Boolean, page: Int): LiveData<List<UserItem>> {
        if (!isOnline)
            return codingChallengeDao.getUsers()!!
        return usersDataSourceImpl.getUsers(page)
    }

    override fun getUser(username: String, onResponse: ((Response<UserItem>) -> Unit)) {
        usersDataSourceImpl.getUser(username, onResponse)
    }


    //Push users to DB - replace old
    override fun persistUsersOnDB(users: List<UserItem>) {
        codingChallengeDao.insertUsers(users)
    }

}