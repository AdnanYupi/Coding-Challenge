package com.codingchallenge.util

import android.app.Application
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.network.ApiService
import com.codingchallenge.repository.repositories.RepositoryPostsImpl
import com.codingchallenge.repository.users.UsersRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

@HiltAndroidApp
class CodeChallengeApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}