package com.codingchallenge.viewControllers.fragments.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.codingchallenge.R
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.util.parseDate
import com.codingchallenge.util.userImageUrl
import kotlinx.android.synthetic.main.fragment_user_details.*

private const val USER_DETAILS = "user_details"

class UserDetailsFragment : Fragment() {
    private var userData: UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userData = it.getParcelable<UserData>(USER_DETAILS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserData()
    }

    private fun checkUserData() {
        if (userData == null) {
            userInfoGroup.visibility = View.GONE
            selectUser.visibility = View.VISIBLE
        } else {
            userInfoGroup.visibility = View.VISIBLE
            selectUser.visibility = View.GONE
            bindUI(userData!!)
        }
    }

    private fun bindUI(userData: UserData) {
        if (userData.name != null)
            username.text = userData.name

        if (userData.email != null) {
            email.text = userData.email
            Glide.with(this)
                .load(userImageUrl(userData.email))
                .into(profileImage)
        }

        if (userData.created_at != null) {
            val parsedDate = parseDate(userData.created_at)
            if (parsedDate != null)
                date.text = parsedDate
        }



    }

    companion object {
        @JvmStatic
        fun newInstance(param1: UserData) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_DETAILS, param1)
                }
            }
    }
}