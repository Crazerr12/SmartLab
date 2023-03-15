package com.example.smartlab.presentation.shoppingcart

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.databinding.ItemShopCartBinding
import com.example.smartlab.presentation.models.ProductModel

class ShopCartViewHolder(private val binding: ItemShopCartBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        product: ProductModel,
        deleteItem: (ProductModel, UInt) -> Unit,
        resources: Resources,
        minusPatient: (ProductModel) -> Unit,
        plusPatient: (ProductModel) -> Unit
    ) = with(binding) {
        minus.isEnabled = false
        var countOfPatient = 1U
        val patient = "пациент"
        title.text = product.name
        price.text = "${product.price} ${resources.getString(R.string.ruble)}"

        delete.setOnClickListener {
            deleteItem(product, countOfPatient)
        }

        minus.setOnClickListener {
            if (countOfPatient == 1U)
                it.isEnabled = false
            else {
                countOfPatient--
                patientsCount.text = "$countOfPatient $patient"
                minusPatient(product)
            }
        }

        plus.setOnClickListener {
            countOfPatient++
            patientsCount.text = "$countOfPatient $patient"
            if (countOfPatient == 2U)
                minus.isEnabled = true
            plusPatient(product)
        }
    }
}