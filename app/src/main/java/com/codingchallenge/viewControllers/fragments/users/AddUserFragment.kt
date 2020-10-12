package com.codingchallenge.viewControllers.fragments.users

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.R
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.model.responses.user.UserResponse
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AddUserFragment : BaseFragment(), KodeinAware {

    private lateinit var usersViewModel: UsersViewModel
    private val usersViewModelFactory: UsersViewModelFactory by instance<UsersViewModelFactory>()
    private var status: String = "Active"
    private var gender: String = "Male"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusesClickListeners()
        genderClickListeners()
        createUser.setOnClickListener {
            if (enterEmail.text == null ||
                    enterName.text == null)
                return@setOnClickListener
            createNewUser()
            popUpFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usersViewModel = ViewModelProvider(this, usersViewModelFactory)
            .get(UsersViewModel::class.java)

    }

    //If network available create user on API
    //else inside DB
    //TODO probably improvement can be done by creating user first in DB and sync with API when possible
    // benefits: can be managed in one request
    private fun createNewUser() {
        if (networkAvailable(requireContext()))
            usersViewModel.createNewUserOnline(createUserData(false))
        else
            //TODO convertible to Kotlin Coroutines
            AsyncTask.execute {
                usersViewModel.createNewUserOffline(createUserData(true))
            }
    }

    private fun statusesClickListeners() {
        active.setOnClickListener {
            active.background = backgroundDrawable(R.drawable.selected_background)
            notActive.background = backgroundDrawable(R.drawable.unselected_background)
            active.setTextColor(getColor(R.color.main_blue))
            notActive.setTextColor(getColor(R.color.faded_txt_color))
            status = "Active"
        }

        notActive.setOnClickListener {
            active.background = backgroundDrawable(R.drawable.unselected_background)
            notActive.background = backgroundDrawable(R.drawable.selected_background)
            active.setTextColor(getColor(R.color.faded_txt_color))
            notActive.setTextColor(getColor(R.color.main_blue))
            status = "InActive"
        }
    }

    private fun genderClickListeners() {
        male.setOnClickListener {
            male.background = backgroundDrawable(R.drawable.selected_background)
            female.background = backgroundDrawable(R.drawable.unselected_background)
            male.setTextColor(getColor(R.color.main_blue))
            female.setTextColor(getColor(R.color.faded_txt_color))
            gender = "Male"
        }
        female.setOnClickListener {
            male.background = backgroundDrawable(R.drawable.unselected_background)
            female.background = backgroundDrawable(R.drawable.selected_background)
            female.setTextColor(getColor(R.color.main_blue))
            male.setTextColor(getColor(R.color.faded_txt_color))
            gender = "Female"
        }
    }

    private fun createUserData(isCreatedOffline: Boolean): UserData {
        return UserData(
            null, enterEmail.text.toString(), gender, null,
            enterName.text.toString(), status, null,
            isCreatedOffline
        )
    }


    override val kodein by closestKodein()

}