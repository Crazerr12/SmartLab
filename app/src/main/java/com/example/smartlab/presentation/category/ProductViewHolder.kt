package com.example.smartlab.presentation.category

import android.content.Context
import android.content.res.Resources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.ItemProductBinding
import com.example.smartlab.presentation.models.ProductModel

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val context: Context,
    private val appBar: ConstraintLayout
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, resources: Resources, onClick: (ProductModel) -> Unit) =
        with(binding) {
            val sharedPreferences = SharedPrefUserStorage(context)

            if (sharedPreferences.getProductsList().contains(product)) {
                addButton.setTextColor(ContextCompat.getColor(context, R.color.active_blue))
                addButton.text = "Убрать"
                addButton.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.white)
            }

            addButton.setOnClickListener {
                if (addButton.text == "Убрать") {
                    val bar = appBar
                    if (sharedPreferences.getProductsList().size == 1) {
                        bar.isVisible = false
                    }
                    addButton.text = resources.getString(R.string.add)
                    addButton.setTextColor(ContextCompat.getColor(context, R.color.white))
                    sharedPreferences.deleteProduct(product)
                    addButton.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.active_blue)
                } else {
                    if (sharedPreferences.getProductsList().isEmpty()){
                        appBar.isVisible = true
                    }
                    sharedPreferences.addProduct(product)
                    addButton.setTextColor(ContextCompat.getColor(context, R.color.active_blue))
                    addButton.text = "Убрать"
                    addButton.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.white)
                }
            }

            cardTitle.text = product.name
            time.text = product.time_result
            price.text = product.price + " " + resources.getString(R.string.ruble)
            root.setOnClickListener {
                onClick(product)
            }
        }
}