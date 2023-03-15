package com.example.smartlab.presentation.analyses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemNewsBinding
import com.example.smartlab.presentation.models.NewsModel

class NewsAdapter() : RecyclerView.Adapter<NewsViewHolder>() {

    private val items = mutableListOf<NewsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news = items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(news: List<NewsModel>) {
        items.clear()
        items.addAll(news)
        notifyDataSetChanged()
    }
}