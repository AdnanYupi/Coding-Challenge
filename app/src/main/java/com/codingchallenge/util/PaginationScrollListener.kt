package com.codingchallenge.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val manager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var scrollDist = 0
    private var isVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = manager.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
        if (!isLoading!! && !isLastPage!!) {
            if (visibleItemCount + firstVisibleItemPosition + 7 >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
        if (isVisible && scrollDist > MINIMUM) {
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -MINIMUM) {
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            scrollDist += dy
        }
    }

    abstract val isLastPage: Boolean?
    abstract val isLoading: Boolean?

    abstract fun loadMoreItems()

    companion object {
        private const val MINIMUM = 10f
    }
}