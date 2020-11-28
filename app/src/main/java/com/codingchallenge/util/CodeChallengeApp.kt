package com.codingchallenge.util

import android.app.Application
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.network.ApiService
import com.codingchallenge.network.datasource.posts.PostsDataSourceImpl
import com.codingchallenge.network.datasource.users.UsersDataSource
import com.codingchallenge.network.datasource.users.UsersDataSourceImpl
import com.codingchallenge.repository.comments.CommentsRepositoryImpl
import com.codingchallenge.repository.posts.PostsRepositoryImpl
import com.codingchallenge.repository.users.UsersRepository
import com.codingchallenge.repository.users.UsersRepositoryImpl
import com.codingchallenge.viewControllers.activities.comments.CommentsViewModelFactory
import com.codingchallenge.viewControllers.fragments.posts.PostsViewModelFactory
import com.codingchallenge.viewControllers.fragments.users.UsersViewModelFactory
import net.danlew.android.joda.JodaTimeAndroid
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CodeChallengeApp : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    //Bind with Kodein
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CodeChallengeApp))

        bind() from singleton { CodingChallengeDatabase(instance()) }
        bind() from singleton { instance<CodingChallengeDatabase>().getCodingDao() }
        bind() from singleton { ApiService() }
        bind<UsersDataSource>() with singleton { UsersDataSourceImpl(instance()) }
        bind<UsersRepository>() with singleton { UsersRepositoryImpl(instance(), instance()) }
        bind() from singleton { UsersRepositoryImpl(instance(), instance()) }
        bind() from singleton { UsersDataSourceImpl(instance()) }
        bind() from provider { UsersViewModelFactory(instance()) }

        bind() from singleton { PostsRepositoryImpl(instance(), instance()) }
        bind() from singleton { PostsDataSourceImpl(instance()) }
        bind() from provider { PostsViewModelFactory(instance()) }

        bind() from singleton { CommentsRepositoryImpl(instance()) }
        bind() from provider { CommentsViewModelFactory(instance()) }

    }
}