package com.example.smartlab.presentation.category

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemProductBinding
import com.example.smartlab.presentation.models.ProductModel

class ProductsAdapter(private val resources: Resources, private val appBar: ConstraintLayout) : RecyclerView.Adapter<ProductViewHolder>() {

    var onClick: (ProductModel) -> Unit = {_ ->}
    private val items = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context, appBar
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product = items[position], resources, onClick)
    }

    override fun getItemCount() = items.size

    fun submitList(products: List<ProductModel>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }
}