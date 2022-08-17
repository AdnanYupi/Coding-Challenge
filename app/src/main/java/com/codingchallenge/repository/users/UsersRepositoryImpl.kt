package com.codingchallenge.repository.users

import androidx.lifecycle.LiveData
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.extensions.resultFlow
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.network.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UsersRepositoryImpl(private val apiService: ApiService
) : UsersRepository {

    /*init {
        //Observe for changes on users
        usersDataSourceImpl.apply {
            usersDataSourceImpl.userResponse.observeForever {
                if (it == null)
                    return@observeForever
            }
        }
    }*/

    //Check is network available
    //If available get users from API
    //else from DB
    override fun getUsers(isOnline: Boolean, page: Int): Flow<ApiResult<List<UserItem>>?> {
        //if (!isOnline)
            //return codingChallengeDao.getUsers()
        return resultFlow {
            apiService.getUsersAsync(page)
        }
    }

    override fun getUser(username: String): Flow<ApiResult<UserItem>?> {
        return resultFlow {
            apiService.getUser(username)
        }
    }


    //Push users to DB - replace old
    override fun persistUsersOnDB(users: List<UserItem>) {
       //TODO codingChallengeDao.insertUsers(users)
    }

}