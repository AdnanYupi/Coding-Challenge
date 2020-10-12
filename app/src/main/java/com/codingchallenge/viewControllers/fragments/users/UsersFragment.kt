package com.codingchallenge.viewControllers.fragments.users

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.R
import com.codingchallenge.adapter.UsersAdapter
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.util.SwipeToDeleteCallback
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.BaseFragment
import com.codingchallenge.viewControllers.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UsersFragment : BaseFragment(), KodeinAware {

    private var dialog: AlertDialog? = null
    private var usersAdapter: UsersAdapter? = null
    private var usersList: ArrayList<UserData> = arrayListOf()

    //TODO IMPROVEMENT: avoid initialisation of ViewModel in each Fragment
    // instance can be shared between Fragments
    // with initialisation inside Activity but, that is not used in this app
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

        addUserBtn.setOnClickListener {
            addFragmentWithBackStack(R.id.mainHost, AddUserFragment())
        }
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

    //Start observing for changes
    //If user is online observe from API
    //Else observe from DB
    //TODO flaws and improvements:
    // possible bugs when switching between states in foreground
    // could be managed in one
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
                usersList.reverse()
                usersAdapter!!.notifyDataSetChanged()

                //Move DB sync to background
                //TODO convertible to Kotlin Coroutines
                AsyncTask.execute {
                    usersViewModel.persistUsersOnDB(usersList)
                }
            }
        })
    }

    private fun initAdapter() {
        usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        usersRecyclerView.setHasFixedSize(true)
        usersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        usersAdapter = UsersAdapter(requireContext(), usersList)
        usersRecyclerView.adapter = usersAdapter
        usersAdapter!!.onUserClick = {
            if (context != null && isAdded) {
                (requireContext() as MainActivity)
                    .selectSecondTab(it)
            }
        }
        attachSwipeDeleteCallback()
    }

    // Pop delete dialog by swiping to the left
    private fun attachSwipeDeleteCallback() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val userData = usersList[viewHolder.adapterPosition]
                if (userData.id != null && isAdded)
                    showUserDeleteDialog(userData.id, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(usersRecyclerView)
    }

    private fun showUserDeleteDialog(userId: Int, position: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete user")
        dialog.setMessage("Are you sure you want to delete this user?")
        dialog.setPositiveButton(
            "Delete"
        ) { d, _ ->
            d.dismiss()
            if (context != null && isAdded) {
                usersViewModel.deleteUser(userId)
                usersList.removeAt(position)
                usersAdapter!!.notifyItemRemoved(position)
            }
        }
        dialog.setNegativeButton("Dismiss") { d, _ ->
            usersAdapter!!.notifyDataSetChanged()
            d.dismiss()
        }
        this.dialog = dialog.create()
        this.dialog!!.show()
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