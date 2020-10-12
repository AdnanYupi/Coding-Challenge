package com.codingchallenge.viewControllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codingchallenge.R
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.viewControllers.fragments.users.UserDetailsFragment
import com.codingchallenge.viewControllers.fragments.users.UsersFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

/**
 * Project written:
 * - using Kotlin
 * - following MVVM architecture
 * - Retrofit
 * - LiveData
 * - Room
 * - Kodein dependency injection
 * - with short methods contains one layer of logic
 *
 * Created Repository - which makes project easily convertible to Kotlin Coroutines
 * Add new user online and offline (possible bugs while switching between this states if app in foreground)
 * If online show users from API else show from DB - sync with DB
 * Delete user online and offline
 * //TODO sync with API if user is removed offline
 *
 * I didn't pay attention on having some fancy app. I was thinking that most important thing is code structure
 */

class MainActivity : AppCompatActivity(), KodeinAware {

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
                        replaceFragment(UsersFragment())
                    }
                    1 -> {
                        //Show nothing
                        //Select user first to see more information
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
        replaceFragment(UsersFragment())
    }

    fun selectSecondTab(userData: UserData) {
        replaceFragment(UserDetailsFragment.newInstance(userData))
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainHost, fragment)
            .commit()
    }

    override val kodein by closestKodein()
}