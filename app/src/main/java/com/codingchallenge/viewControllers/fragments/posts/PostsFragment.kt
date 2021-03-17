package com.codingchallenge.viewControllers.fragments.posts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PostsFragment : BaseFragment(R.layout.fragment_posts), KodeinAware {

    private lateinit var postsViewModel: PostsViewModel
    private val postsViewModelFactory: PostsViewModelFactory by instance<PostsViewModelFactory>()
    private var postItems = arrayListOf<RepositoriesItem>()
    private var adapter: GenericAdapter<RepositoriesItem>? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postsViewModel = ViewModelProvider(this, postsViewModelFactory)
            .get(PostsViewModel::class.java)
        observeForPosts(networkAvailable(requireContext()))
    }

    private fun observeForPosts(isOnline: Boolean) {
        postsViewModel.getPosts(isOnline)
            .observe(viewLifecycleOwner, Observer {
                if (it == null)
                    return@Observer

                if (postItems.isNotEmpty())
                    postItems.clear()

                postItems.addAll(it)
                initRecyclerView()

            })
    }

    private fun initRecyclerView() {
        if (adapter == null) {
            postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            postsRecyclerView.setHasFixedSize(true)
            postsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = generateAdapter()
            postsRecyclerView.adapter = adapter

            adapter?.onPostClick = {
                if (context != null && isAdded) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url))
                    requireContext().startActivity(intent)
                }
            }

        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun generateAdapter(): GenericAdapter<RepositoriesItem> {
        return object: GenericAdapter<RepositoriesItem>(postItems) {
            override fun create(item: RepositoriesItem, viewHolder: ViewHolder) {

                viewHolder.itemView.profileImage.visibility = View.GONE

                if (item.name != null)
                    viewHolder.itemView.username.text = "Name: ${item.name}"
                if (item.description != null)
                    viewHolder.itemView.userEmail.text = item.description

                viewHolder.itemView.createdDate.text = "Post id: ${item.id}"
            }

            override fun getItemViewType(position: Int): Int {
                return R.layout.user_cell
            }

        }
    }


    override val kodein: Kodein by closestKodein()

}