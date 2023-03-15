package com.example.smartlab.presentation.order

import android.content.res.Resources
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.databinding.ItemAnalysisBinding
import com.example.smartlab.presentation.models.ProductModel

class AnalysisViewHolder(private val binding: ItemAnalysisBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: ProductModel,
        resources: Resources,
        countOfAnalyses: (Int) -> Unit,
        addAnalyses: (Int) -> Unit,
        removeAnalyses: (Int) -> Unit
    ) = with(binding) {
        countOfAnalyses(product.price.toInt())
        checkBox.isChecked = true
        name.text = product.name
        price.text = "${product.price} ${resources.getString(R.string.ruble)}"
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addAnalyses(product.price.toInt())
                name.setTextColor(Color.BLACK)
                price.setTextColor(Color.BLACK)
            } else {
                removeAnalyses(product.price.toInt())
                name.setTextColor(Color.GRAY)
                price.setTextColor(Color.GRAY)
            }
        }
    }
}