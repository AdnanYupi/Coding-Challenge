package com.codingchallenge.viewControllers.fragments.posts

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.model.responses.post.PostItem
import com.codingchallenge.util.networkAvailable
import com.codingchallenge.viewControllers.activities.comments.CommentsActivity
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PostsFragment : Fragment(), KodeinAware {

    private lateinit var postsViewModel: PostsViewModel
    private val postsViewModelFactory: PostsViewModelFactory by instance<PostsViewModelFactory>()
    private var postItems = arrayListOf<PostItem>()
    private var adapter: GenericAdapter<PostItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }


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

                AsyncTask.execute {
                    postsViewModel.pushPostsToDB(postItems)
                }
            })
    }

    private fun initRecyclerView() {
        if (adapter == null) {
            postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            postsRecyclerView.setHasFixedSize(true)
            postsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = generateAdapter()
            postsRecyclerView.adapter = adapter
            adapter!!.onPostClick = {

                if (context != null && isAdded) {
                    val postId = it.id
                    val intent = Intent(requireContext(), CommentsActivity::class.java)
                    intent.putExtra("postId", postId)
                    requireContext().startActivity(intent)
                }
            }

        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun generateAdapter(): GenericAdapter<PostItem> {
        return object: GenericAdapter<PostItem>(postItems) {
            override fun create(item: PostItem, viewHolder: ViewHolder) {
                if (item.title != null)
                    viewHolder.itemView.username.text = item.title
                if (item.body != null)
                    viewHolder.itemView.userEmail.text = item.body

                viewHolder.itemView.createdDate.text = "Post id: ${item.id}"
            }

            override fun getItemViewType(position: Int): Int {
                return R.layout.user_cell
            }

        }
    }


    override val kodein: Kodein by closestKodein()

}