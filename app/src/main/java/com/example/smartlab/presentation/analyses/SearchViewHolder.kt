package com.example.smartlab.presentation.analyses

import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.databinding.ItemSearchBinding
import com.example.smartlab.presentation.models.ProductModel

class SearchViewHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductModel, resources: Resources) = with(binding) {

        nameOfCard.text = product.name
        days.text = product.time_result
        price.text = "${product.price} ${resources.getString(R.string.ruble)}"
    }

    fun bind(product: ProductModel, resources: Resources, substring: String?, context: Context) =
        with(binding) {
            val name = product.name
            val spannable = SpannableString(name)
            val startIndex = substring?.let { name.indexOf(it) }
            val endIndex = startIndex?.plus(substring.length)
            if (endIndex != null) {
                spannable.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            context,
                            R.color.active_blue
                        )
                    ), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            nameOfCard.text = spannable
            days.text = product.time_result
            price.text = "${product.price} ${resources.getString(R.string.ruble)}"
        }
}