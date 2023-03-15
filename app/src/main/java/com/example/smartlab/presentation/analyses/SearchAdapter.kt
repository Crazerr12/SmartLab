package com.example.smartlab.presentation.analyses

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemSearchBinding
import com.example.smartlab.presentation.models.ProductModel

class SearchAdapter(private val resources: Resources, private val context: Context) : RecyclerView.Adapter<SearchViewHolder>() {

    private var substring: String? = null
    private val items = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (substring != null) holder.bind(items[position], resources, substring, context)
        else holder.bind(items[position], resources)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<ProductModel>){
        substring = null
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    fun submitList(products: List<ProductModel>, string: String?){
        substring = string
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }
}