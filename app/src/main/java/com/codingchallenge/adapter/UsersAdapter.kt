package com.codingchallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.R
import com.codingchallenge.model.responses.user.UserData
import com.codingchallenge.util.parseDate
import kotlinx.android.synthetic.main.user_cell.view.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UsersAdapter(private val context: Context, private val users: List<UserData>) :
    RecyclerView.Adapter<UsersAdapter.UsersHolder>() {

    var onUserClick: ((UserData) -> Unit)? = null

    inner class UsersHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var username: TextView
        lateinit var userEmail: TextView
        lateinit var userCreationDate: TextView

        init {
            username = view.findViewById(R.id.username)
            userEmail = view.findViewById(R.id.userEmail)
            userCreationDate = view.findViewById(R.id.createdDate)

            view.setOnClickListener {
                onUserClick?.invoke(users[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_cell, parent, false)
        return UsersHolder(view)
    }

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        val user = users[position]

        if (user.name != null)
            holder.username.text = user.name
        if (user.email != null)
            holder.userEmail.text = user.email

        if (user.created_at != null) {
            val parsedDate = parseDate(user.created_at)
            if (parsedDate != null)
                holder.userCreationDate.text = parsedDate
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}