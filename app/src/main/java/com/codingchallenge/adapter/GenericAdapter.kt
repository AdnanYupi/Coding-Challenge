package com.codingchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T>(private var items: ArrayList<T>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun create(item: T, viewHolder: ViewHolder)
    var onPostClick: ((T) -> Unit)? = null

    fun update(items: ArrayList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        create(items[position], holder as GenericAdapter<T>.ViewHolder)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                if (it == null)
                    return@setOnClickListener
                onPostClick?.invoke(items[adapterPosition])
            }
        }
    }
}