package com.example.smartlab.presentation.shoppingcart

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemShopCartBinding
import com.example.smartlab.presentation.models.ProductModel

class ShoppingCartAdapter(private val resources: Resources) : RecyclerView.Adapter<ShopCartViewHolder>() {

    var deleteItem: (ProductModel, UInt) -> Unit = { _, _ -> }
    var minusPatient: (ProductModel) -> Unit = {_ ->}
    var plusPatient: (ProductModel) -> Unit = {_ ->}
    private val items = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCartViewHolder {
        return ShopCartViewHolder(
            ItemShopCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShopCartViewHolder, position: Int) {
        holder.bind(items[position], deleteItem, resources, minusPatient, plusPatient)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<ProductModel>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    fun removeItem(product: ProductModel) {
        items.remove(product)
        notifyDataSetChanged()
    }
}