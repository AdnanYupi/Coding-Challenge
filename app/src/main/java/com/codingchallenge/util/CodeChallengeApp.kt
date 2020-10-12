package com.codingchallenge.util

import android.app.Application
import com.codingchallenge.database.CodingChallengeDatabase
import com.codingchallenge.network.ApiService
import com.codingchallenge.network.datasource.UsersDataSource
import com.codingchallenge.network.datasource.UsersDataSourceImpl
import com.codingchallenge.network.interceptor.AuthInterceptor
import com.codingchallenge.network.interceptor.AuthInterceptorImpl
import com.codingchallenge.repository.UsersRepository
import com.codingchallenge.repository.UsersRepositoryImpl
import com.codingchallenge.viewControllers.fragments.users.UsersViewModelFactory
import net.danlew.android.joda.JodaTimeAndroid
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CodeChallengeApp: Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    //Bind with Kodein
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CodeChallengeApp))

        bind() from  singleton { CodingChallengeDatabase(instance()) }
        bind() from  singleton { instance<CodingChallengeDatabase>().getCodingDao() }
        bind() from  singleton { ApiService() }
        bind<UsersDataSource>() with  singleton { UsersDataSourceImpl(instance()) }
        bind<UsersRepository>() with  singleton { UsersRepositoryImpl(instance(), instance()) }
        bind<AuthInterceptor>() with singleton { AuthInterceptorImpl() }
        bind() from  singleton { UsersRepositoryImpl(instance(), instance()) }
        bind() from  singleton { UsersDataSourceImpl(instance()) }
        bind() from provider { UsersViewModelFactory(instance()) }

    }
}