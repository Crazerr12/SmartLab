package com.example.smartlab.presentation.order

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.databinding.ItemAnalysisBinding
import com.example.smartlab.presentation.models.ProductModel

class AnalysesAdapter(private val resources: Resources) :
    RecyclerView.Adapter<AnalysisViewHolder>() {

    var countOfAnalyses: (Int) -> Unit = { _ -> }
    var removeAnalyses: (Int) -> Unit = { _ -> }
    var addAnalyses: (Int) -> Unit = { _ -> }
    private val items = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder {
        return AnalysisViewHolder(
            ItemAnalysisBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int) {
        holder.bind(items[position], resources, countOfAnalyses, addAnalyses, removeAnalyses)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<ProductModel>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

}