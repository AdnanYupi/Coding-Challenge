package com.codingchallenge.presentation.fragments.users

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.databinding.FragmentUsersBinding
import com.codingchallenge.model.responses.user.UserItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.util.PaginationScrollListener
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.presentation.BaseFragment
import com.codingchallenge.presentation.activities.UserDetailActivity
import com.codingchallenge.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>(R.layout.fragment_users) {

    private var dialog: AlertDialog? = null
    private var adapter: GenericAdapter<UserItem>? = null
    private var pageIndex = 0
    private var isLoadingPage: Boolean = false


    override val viewModel: UsersViewModel by activityViewModels()

    override fun inflated(binding: FragmentUsersBinding) {
        initAdapter()
        if (viewModel.usersList.isEmpty())
            fetchUsers(networkAvailable(requireContext()), page = 0)

    }


    private fun fetchUsers(isOnline: Boolean, page: Int) {
        isLoadingPage = true
        val users = viewModel.getUsers(isOnline, page)
        users.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer
            isLoadingPage = false
            if (it is ApiResult.Success) {
                it.data?.let { users ->
                    viewModel.usersList.addAll(users)
                    if (adapter != null)
                        adapter?.notifyDataSetChanged()
                }
            } else if (it is ApiResult.Error) {
                //TODO handle error
            }
            /*  if (usersList.isNotEmpty())
                  usersList.clear()*/

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
        return object : GenericAdapter<UserItem>(viewModel.usersList) {
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

}