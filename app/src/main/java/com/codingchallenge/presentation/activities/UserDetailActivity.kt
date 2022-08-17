package com.codingchallenge.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import com.bumptech.glide.Glide
import com.codingchallenge.R
import com.codingchallenge.network.ApiResult
import com.codingchallenge.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_comments.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private val userViewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val username = intent.getStringExtra("username")

        if (!username.isNullOrEmpty())
            fetchUser(username)
    }

    private fun fetchUser(username: String) {
        userViewModel.getUser(username)
            .observe(this) {
                if (it is ApiResult.Success) {
                    it.data?.let { user ->
                        if (user.avatarUrl != null)
                            Glide.with(this)
                                .load(user.avatarUrl)
                                .into(profileImage)

                        if (user.userLogin != null)
                            userName.text = user.userLogin

                        followers.text = "Followers: ${user.followers}"
                        following.text = "Following: ${user.following}"
                    }
                }
            }

    }

}