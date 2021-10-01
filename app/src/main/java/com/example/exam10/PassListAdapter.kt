package com.example.exam10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PassListAdapter(private val quoteList: List<Item>): RecyclerView.Adapter<PassListAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val login: TextView = itemView.findViewById(R.id.tv_login)
        val password: TextView = itemView.findViewById(R.id.tv_pass)
        val url: TextView = itemView.findViewById(R.id.tv_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pass, parent, false)
        return QuoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentItem = quoteList[position]
        holder.login.text = currentItem.login
        holder.password.text = currentItem.password
        holder.url.text = currentItem.url
    }

    override fun getItemCount() = quoteList.size

}