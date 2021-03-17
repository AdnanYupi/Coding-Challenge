package com.codingchallenge.viewControllers.fragments.users

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.model.responses.user.User
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.util.PaginationScrollListener
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.BaseFragment
import com.codingchallenge.viewControllers.activities.UserDetailActivity
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UsersFragment : BaseFragment(R.layout.fragment_users), KodeinAware {

    private var dialog: AlertDialog? = null
    private var adapter: GenericAdapter<UserItem>? = null
    private var usersList: User = User()
    private var pageIndex = 0
    private var isLoadingPage: Boolean = false
    private lateinit var usersViewModel: UsersViewModel
    private val usersViewModelFactory: UsersViewModelFactory by instance()


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
        fetchUsers(networkAvailable(requireContext()), page = 0)
    }


    private fun fetchUsers(isOnline: Boolean, page: Int) {
        setProgressBarVisibility(View.VISIBLE)
        isLoadingPage = true
        val users = usersViewModel.getUsers(isOnline, page)
        users.observe(viewLifecycleOwner, Observer {
            setProgressBarVisibility(View.GONE)
            if (it == null)
                return@Observer
            isLoadingPage = false

            /*  if (usersList.isNotEmpty())
                  usersList.clear()*/

            usersList.addAll(it)
            initAdapter()
            //Move DB sync to background
            AsyncTask.execute {
                usersViewModel.persistUsersOnDB(usersList)
            }

        })
    }


    private fun initAdapter() {
        if (adapter == null) {
            val manager = LinearLayoutManager(requireContext())
            usersRecyclerView.layoutManager = manager
            usersRecyclerView.setHasFixedSize(true)
            usersRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = generateAdapter()
            usersRecyclerView.adapter = adapter
            attachScrollListener(manager)
            adapter?.onPostClick = {
                val intent = Intent(requireContext(), UserDetailActivity::class.java)
                intent.putExtra("username", it.userLogin)
                startActivity(intent)
            }
        } else {
            adapter?.notifyDataSetChanged()
        }
    }

    private fun attachScrollListener(manager: LinearLayoutManager) {
        val scrollListener = object : PaginationScrollListener(manager) {
            override val isLastPage: Boolean?
                get() = false

            override val isLoading: Boolean?
                get() = isLoadingPage

            override fun loadMoreItems() {
                pageIndex++
                Log.d("PageIndex", "$pageIndex")
                fetchUsers(networkAvailable(requireContext()), pageIndex)
            }
        }
        usersRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun generateAdapter(): GenericAdapter<UserItem> {
        return object : GenericAdapter<UserItem>(usersList) {
            override fun create(item: UserItem, viewHolder: ViewHolder) {
                if (item.userLogin != null)
                    viewHolder.itemView.username.text = item.userLogin
                if (item.type != null)
                    viewHolder.itemView.userEmail.text = item.type

                if (item.avatarUrl != null)
                    Glide.with(requireContext())
                        .load(item.avatarUrl)
                        .into(viewHolder.itemView.profileImage)

                viewHolder.itemView.createdDate.text = "User id: ${item.userId}"
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