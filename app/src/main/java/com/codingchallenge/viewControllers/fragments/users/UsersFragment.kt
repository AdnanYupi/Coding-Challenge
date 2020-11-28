package com.codingchallenge.viewControllers.fragments.users

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.BaseFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UsersFragment : BaseFragment(), KodeinAware {

    private var dialog: AlertDialog? = null
    private var adapter: GenericAdapter<UserItem>? = null
    private var usersList: User = User()

    private lateinit var usersViewModel: UsersViewModel
    private val usersViewModelFactory: UsersViewModelFactory by instance<UsersViewModelFactory>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        usersViewModel = ViewModelProvider(this, usersViewModelFactory)
            .get(UsersViewModel::class.java)
        setProgressBarVisibility(View.VISIBLE)
        fetchUsers(networkAvailable(requireContext()))
    }


    private fun fetchUsers(isOnline: Boolean) {
        val users = usersViewModel.getUsers(isOnline)
        users.observe(viewLifecycleOwner, Observer {
            setProgressBarVisibility(View.GONE)
            if (it == null)
                return@Observer

            val userData = it
            if (isAdded && context != null) //This shouldn't be required because of lifecycleOwner but just in case
            {
                if (usersList.isNotEmpty())
                    usersList.clear()

                usersList.addAll(userData)
                initAdapter()
                //Move DB sync to background
                AsyncTask.execute {
                    usersViewModel.persistUsersOnDB(usersList)
                }
            }
        })
    }

    private fun initAdapter() {
        if (adapter == null) {
            usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            usersRecyclerView.setHasFixedSize(true)
            usersRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = generateAdapter()
            usersRecyclerView.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun generateAdapter(): GenericAdapter<UserItem> {
        return object: GenericAdapter<UserItem>(usersList) {
            override fun create(item: UserItem, viewHolder: ViewHolder) {
                if (item.name != null)
                    viewHolder.itemView.username.text = item.name
                if (item.email != null)
                    viewHolder.itemView.userEmail.text = item.email

                viewHolder.itemView.createdDate.text = "User id: ${item.id}"
            }

            override fun getItemViewType(position: Int): Int {
                return R.layout.user_cell
            }

        }
    }


    private fun setProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }

    // Check dialog ea. user minimised the app with dialog created
    // remove and prevent leak
    override fun onDestroy() {
        if (dialog != null)
            dialog!!.dismiss()
        super.onDestroy()
    }

    override val kodein by closestKodein()

}