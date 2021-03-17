package com.codingchallenge.util

import android.app.Application
import com.codingchallenge.database.CodingChallengeDao
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.network.ApiService
import com.codingchallenge.network.datasource.repositories.RepositoriesDataSource
import com.codingchallenge.network.datasource.repositories.RepositoriesDataSourceImpl
import com.codingchallenge.network.datasource.users.UsersDataSource
import com.codingchallenge.network.datasource.users.UsersDataSourceImpl
import com.codingchallenge.repository.repositories.RepositoryPostsImpl
import com.codingchallenge.repository.users.UsersRepositoryImpl
import com.codingchallenge.viewControllers.fragments.posts.PostsViewModelFactory
import com.codingchallenge.viewControllers.fragments.users.UsersViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CodeChallengeApp: Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@CodeChallengeApp))

        bind<ApiService>() with singleton { ApiService() }
        bind<CodingChallengeDatabase>() with singleton { CodingChallengeDatabase(instance()) }
        bind<CodingChallengeDao>() with singleton { instance<CodingChallengeDatabase>().getCodingDao() }
        bind<UsersDataSource>() with  singleton { UsersDataSourceImpl(instance()) }
        bind() from singleton { UsersDataSourceImpl(instance()) }

        bind<RepositoriesDataSource>() with singleton { RepositoriesDataSourceImpl(instance()) }
        bind() from singleton { RepositoriesDataSourceImpl(instance()) }

        bind<RepositoryPostsImpl>() with singleton { RepositoryPostsImpl(instance(), instance()) }
        bind<UsersRepositoryImpl>() with singleton { UsersRepositoryImpl(instance(), instance()) }
        bind<PostsViewModelFactory>() with singleton { PostsViewModelFactory(instance()) }
        bind<UsersViewModelFactory>() with singleton { UsersViewModelFactory(instance()) }
    }
}