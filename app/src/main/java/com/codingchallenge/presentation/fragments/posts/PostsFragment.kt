package com.codingchallenge.presentation.fragments.posts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.databinding.FragmentPostsBinding
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.network.ApiResult
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.presentation.BaseFragment
import com.codingchallenge.viewmodels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel>(R.layout.fragment_posts) {

    private val postsViewModel: PostsViewModel by activityViewModels()
    private var adapter: GenericAdapter<RepositoriesItem>? = null


    override val viewModel: PostsViewModel by activityViewModels()

    override fun inflated(binding: FragmentPostsBinding) {
        if (viewModel.postItems.isEmpty())
            observeForPosts(networkAvailable(requireContext()))

        initAdapter()

    }


    private fun observeForPosts(isOnline: Boolean) {
        postsViewModel.getPosts(isOnline)
            .observe(viewLifecycleOwner, Observer {
                if (it == null)
                    return@Observer

                if (it is ApiResult.Success) {
                    viewModel.postItems.addAll(it.data!!)
                    if (adapter != null)
                        adapter?.notifyDataSetChanged()
                } else if (it is ApiResult.Error) {
                    //TODO handle error
                }

            })
    }

    private fun initAdapter() {
        if (adapter == null) {
            postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            postsRecyclerView.setHasFixedSize(true)
            postsRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = generateAdapter()
            postsRecyclerView.adapter = adapter

            adapter?.onPostClick = {
                if (context != null && isAdded) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url))
                    requireContext().startActivity(intent)
                }
            }

        }
    }

    private fun generateAdapter(): GenericAdapter<RepositoriesItem> {
        return object : GenericAdapter<RepositoriesItem>(viewModel.postItems) {
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

}