package com.codingchallenge.viewControllers.activities.comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.R
import com.codingchallenge.adapter.GenericAdapter
import com.codingchallenge.model.responses.comment.CommentItem
import com.codingchallenge.model.responses.post.PostItem
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.user_cell.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class CommentsActivity : AppCompatActivity(), KodeinAware {

    private lateinit var commentsViewModel: CommentsViewModel
    private val commentsViewModelFactory by instance<CommentsViewModelFactory>()
    private var comments = arrayListOf<CommentItem>()
    private var adapter: GenericAdapter<CommentItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val postId = intent.getIntExtra("postId", -1)
        if (postId != -1)
            initViewModel(postId)
    }

    private fun initViewModel(id: Int) {
        Log.d("Comments", "PostId: $id")
        commentsViewModel = ViewModelProvider(this, commentsViewModelFactory)
            .get(CommentsViewModel::class.java)
        commentsViewModel.getComments(id)
            .observe(this, Observer {
                if (it == null)
                    return@Observer
                if (comments.isNotEmpty())
                    comments.clear()

                comments.addAll(it)
                initAdapter()
            })
    }

    private fun initAdapter() {
        if (adapter == null) {
            commentsRecyclerView.layoutManager = LinearLayoutManager(this)
            commentsRecyclerView.setHasFixedSize(true)
            commentsRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            adapter = generateAdapter()
            commentsRecyclerView.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }


    private fun generateAdapter(): GenericAdapter<CommentItem> {
        return object: GenericAdapter<CommentItem>(comments) {
            override fun create(item: CommentItem, viewHolder: ViewHolder) {
                if (item.name != null)
                    viewHolder.itemView.username.text = item.name
                if (item.body != null)
                    viewHolder.itemView.userEmail.text = item.body

                viewHolder.itemView.createdDate.text = "Comment id: ${item.id}"
            }

            override fun getItemViewType(position: Int): Int {
                return R.layout.user_cell
            }

        }
    }

    override val kodein: Kodein by closestKodein()
}