package com.codingchallenge.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.codingchallenge.R
import com.codingchallenge.presentation.fragments.posts.PostsFragment
import com.codingchallenge.presentation.fragments.users.UsersFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Project written:
 * - using Kotlin (Coroutines and Flow)
 * - following MVVM architecture
 * - Retrofit
 * - LiveData
 * - Room
 * - Hilt dependency injection
 * - with short methods contains one layer of logic
 *
 * Created Repository - which makes project easily convertible to Kotlin Coroutines
 * If online show users from API else show from DB - sync with DB
 *
 * I didn't pay attention on having some fancy app. I was thinking that most important thing is code structure
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTabLayout()
    }

    private fun initTabLayout() {
        selectFirstTab()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                        replaceFragment(PostsFragment())
                    }
                    1 -> {
                        replaceFragment(UsersFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    private fun selectFirstTab() {
        tabLayout.selectTab(tabLayout.getTabAt(0), true)
        replaceFragment(PostsFragment())
    }



    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainHost, fragment)
            .commit()
    }

}