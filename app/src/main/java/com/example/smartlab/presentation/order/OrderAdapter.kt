package com.example.smartlab.presentation.order

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.FragmentMakingOrderBinding
import com.example.smartlab.databinding.ItemOrderBinding

class OrderAdapter(
    private val resources: Resources,
    private val binding: FragmentMakingOrderBinding
) : RecyclerView.Adapter<OrderViewHolder>() {

    var removeCard: () -> Unit = {}
    var choosePatient: (ItemOrderBinding) -> Unit = { _ -> }
    var sumOfAnalyses: (Int) -> Unit = { _ -> }
    var removeCheck: (Int) -> Unit = { _ -> }
    var addCheck: (Int) -> Unit = { _ -> }
    private var items: Int = 0
    private val notEmptyList = mutableListOf<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(
            resources,
            position,
            binding,
            removeCard,
            choosePatient,
            sumOfAnalyses,
            removeCheck,
            addCheck,
            notEmptyList
        )
    }

    override fun getItemCount(): Int = items

    fun submitList(users: Int) {
        items = users
        notEmptyList.clear()
        for (i in 0 until items)
            notEmptyList.add(false)
        notifyDataSetChanged()
    }

    fun deleteItem() {
        items--
        notifyDataSetChanged()
    }

    fun getListChangedEditText(): List<Boolean> = notEmptyList
}