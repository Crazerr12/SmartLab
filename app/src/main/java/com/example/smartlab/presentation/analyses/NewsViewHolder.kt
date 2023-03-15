package com.example.smartlab.presentation.analyses

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemNewsBinding
import com.example.smartlab.presentation.models.NewsModel
import com.squareup.picasso.Picasso

class NewsViewHolder(private val binding: ItemNewsBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsModel) = with(binding){
            cardTitle.text = news.name
            cardInfo.text = news.description
            cardPrice.text = news.price
            Picasso.with(context).load(news.image).into(cardImage)
        }

}