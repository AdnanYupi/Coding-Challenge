package com.codingchallenge.viewControllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codingchallenge.R
import com.codingchallenge.viewControllers.fragments.users.UsersViewModel
import com.codingchallenge.viewControllers.fragments.users.UsersViewModelFactory
import kotlinx.android.synthetic.main.activity_comments.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class UserDetailActivity : AppCompatActivity(), KodeinAware {

    private lateinit var userViewModel: UsersViewModel
    private val usersViewModelFactory by instance<UsersViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val username = intent.getStringExtra("username")

        if (!username.isNullOrEmpty())
            initViewModel(username)
    }

    private fun initViewModel(username: String) {
        userViewModel = ViewModelProvider(this, usersViewModelFactory)
            .get(UsersViewModel::class.java)
        userViewModel.getUser(username, userResponse = {
            if (it.isSuccessful && it.body() != null) {
                val user = it.body()!!
                if (user.avatarUrl != null)
                    Glide.with(this)
                        .load(user.avatarUrl)
                        .into(profileImage)

                if (user.userLogin != null)
                    userName.text = user.userLogin

                followers.text = "Followers: ${user.followers}"
                following.text = "Following: ${user.following}"
            }
        })
    }


    override val kodein: Kodein by closestKodein()
}